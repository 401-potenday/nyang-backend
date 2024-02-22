package potenday.app.acceptance.comment;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import potenday.app.acceptance.AcceptanceTest;
import potenday.app.domain.auth.TokenProvider;
import potenday.app.domain.cat.content.CatContent;
import potenday.app.domain.cat.content.CatContentRepository;
import potenday.app.domain.cat.status.CatFriends;
import potenday.app.domain.cat.status.CatNeuter;
import potenday.app.domain.cat.status.CatPersonalities;
import potenday.app.domain.cat.vo.Coordinate;
import potenday.app.domain.cat.vo.JibunAddress;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserOAuthProvider;
import potenday.app.domain.user.UserRepository;

@DisplayName("고양이 컨텐츠 댓글 인수 테스트")
public class CommentAcceptanceTest extends AcceptanceTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private CatContentRepository catContentRepository;

  @Test
  @DisplayName("고양이 컨텐츠 댓글을 성공적으로 등록한다. - 성공")
  void registerCourse() {
    saveMember();
    saveContent(1L);

    given().log().all().contentType(ContentType.JSON)

        // when
        .when().header("Authorization", String.format("Bearer %s", userToken(1L))).body("""
            {
                "commentImageKeys": [
                  "0cb9b066-5d37-4788-8695-7fd929e06c21",
                  "0cb9b066-5d37-4788-8695-7fd929e06c23",
                  "0cb9b066-5d37-4788-8695-7fd929e06c23"
                  ],
                "commentDesc": "this is comment desc"
            }
            """).post("/contents/{contentId}/comments", 1L)

        // then
        .then()
          .log().all()
          .assertThat().statusCode(200)
          .assertThat().body("result", is("SUCCESS"))
          .assertThat().body("data.commentId", is(1));

  }

  private void saveMember() {
    userRepository.saveAndFlush(activeUser());
  }

  private void saveContent(long userId) {
    catContentRepository.saveAndFlush(catContent(userId));
  }

  private CatContent catContent(long userId) {
    CatContent catContent = CatContent.builder()
        .id(1L)
        .catPersonalities(CatPersonalities.of(List.of("UNSURE")))
        .name("닝닝이")
        .catEmoji(10)
        .neuter(CatNeuter.NO)
        .hasFriends(CatFriends.NO)
        .coordinate(
            Coordinate.builder()
                .lat(37.11111)
                .lon(128.11111)
                .build()
        )
        .description("description")
        .jibunAddress(JibunAddress.builder()
            .jibunAddrName("jibun addr name")
            .build())
        .build();
    catContent.setOwner(userId);
    return catContent;
  }

  private String userToken(long id) {
    return tokenProvider.issueAccessToken(id);
  }

  private User activeUser() {
    User user = User.builder()
        .id(1L)
        .userOAuthProvider(UserOAuthProvider.KAKAO)
        .oAuthUid("oauth-uid")
        .build();
    user.updateNickname("nickname");
    return user;
  }
}
