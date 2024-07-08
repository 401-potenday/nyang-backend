package potenday.app.acceptance.auth;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.restassured.http.ContentType;
import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import potenday.app.EmbeddedRedisConfig;
import potenday.app.acceptance.AcceptanceTest;
import potenday.app.domain.auth.TokenProvider;

@DisplayName("인증 인수테스트")
@Import(EmbeddedRedisConfig.class)
public class AuthAcceptanceTest extends AcceptanceTest {

  @Autowired
  StringRedisTemplate redisTemplate;

  @Autowired
  TokenProvider tokenProvider;

  @Test
  @DisplayName("OAuth 로그인 URI 요청 - 성공")
  void requestOauthLoginUri() {

    given()
      .log()
      .all()
      .contentType(ContentType.JSON)

        // when
    .when()
      .get("/auth/{provider}/oauth-uri?redirect_uri={requestUri}", "kakao", "http://localhost:3000")

        // then
    .then()
      .log().all()
      .assertThat().statusCode(200)
      .assertThat().body("result", is("SUCCESS"))
      .assertThat().body("data.provider", is("KAKAO"))
      .assertThat().body("data.oauthUri", is("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=client-id-sample&redirect_uri=http://localhost:3000"));
  }

  @Test
  @DisplayName("로그아웃 요청 - 성공")
  void logoutSuccessTest() {
    // given
    final String refreshToken = tokenProvider.issueAccessToken(1L);
    saveToken(refreshToken);
    given()
          .log()
          .all()
          .contentType(ContentType.JSON)

    // when
    .when()
      .header("Authorization", String.format("Bearer %s", userToken(1L)))
      .body(String.format("""
        {
          "refreshToken": "%s"
        }
        """, refreshToken))
      .post("/auth/logout")

    // then
    .then()
      .log().all()
      .assertThat().statusCode(200)
      .assertThat().body("result", is("SUCCESS"));
  }

  @Test
  @DisplayName("다른 유저의 refreshToken 로그아웃 요청 시 예외 발생 - 실패")
  void logoutFailAnotherUserRefreshTokenTest() {
    // given
    final String refreshToken = tokenProvider.issueAccessToken(1L);
    saveToken(refreshToken);
    given()
              .log()
              .all()
              .contentType(ContentType.JSON)

        // when
        .when()
          .header("Authorization", String.format("Bearer %s", userToken(2L)))
          .body(String.format("""
          {
            "refreshToken": "%s"
          }
          """, refreshToken))
          .post("/auth/logout")

        // then
        .then()
          .log().all()
          .assertThat().statusCode(401)
          .assertThat().body("result", is("ERROR"));
  }

  private void saveToken(String refreshToken) {
    redisTemplate.opsForValue().set(refreshToken, "1", Duration.ofSeconds(1000L));
  }

  private String userToken(long id) {
    return tokenProvider.issueAccessToken(id);
  }
}
