package potenday.app.acceptance.user;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import potenday.app.acceptance.AcceptanceTest;
import potenday.app.domain.auth.TokenProvider;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserOAuthProvider;
import potenday.app.domain.user.UserRepository;

@DisplayName("유저 인수테스트")
public class UserAcceptanceTest extends AcceptanceTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenProvider tokenProvider;

  @Test
  @DisplayName("유저 닉네임을 조회한다. - 성공")
  void getUserNickname() {
    // given
    final String nickname = "my_nickname";
    saveMemberWithNickname(nickname);
    given().contentType(ContentType.JSON)

        // when
        .when()
          .header("Authorization", String.format("Bearer %s", userToken(1L)))
          .get("/user/nickname")

        // then
        .then()
          .assertThat().body("result", is("SUCCESS"))
          .assertThat().body("data.nickname", is(nickname))
          .assertThat().statusCode(200);
  }

  @Test
  @DisplayName("유저 닉네임이 없으면 nickname 은 null 을 반환한다. - 성공")
  void getUserNicknameNull() {
    // given
    saveMemberWithNickname(null);

    // when
    Response response = given()
        .contentType(ContentType.JSON)
        .header("Authorization", String.format("Bearer %s", userToken(1L)))
        .get("/user/nickname")
        .then().extract().response();

    // then
    assertThat(response.getStatusCode()).isEqualTo(200);
    assertThat(response.jsonPath().getString("result")).isEqualTo("SUCCESS");
    assertThat(response.jsonPath().getString("data.nickname")).isNull();
  }

  private void saveMemberWithNickname(String nickname) {
    userRepository.saveAndFlush(activeUser(nickname));
  }

  private String userToken(long id) {
    return tokenProvider.issueAccessToken(id);
  }

  private User activeUser(String nickname) {
    User user = User.builder()
        .id(1L)
        .userOAuthProvider(UserOAuthProvider.KAKAO)
        .oAuthUid("oauth-uid")
        .build();
    user.updateNickname(nickname);
    return user;
  }
}
