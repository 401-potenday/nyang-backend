package potenday.app.acceptance.auth;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.http.ContentType;
import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.TestPropertySource;
import potenday.app.EmbeddedRedisConfig;
import potenday.app.acceptance.AcceptanceTest;
import potenday.app.domain.auth.TokenProvider;

@DisplayName("인증 토큰 재발급 인수테스트")
@Import(value = EmbeddedRedisConfig.class)
@TestPropertySource(
    locations = "classpath:application.yaml",
    properties = {
        "jwt.access-time-sec = 1000",
        "jwt.refresh-time-sec = 2"
    }
)
public class AuthRefreshAcceptanceTest extends AcceptanceTest {

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private TokenProvider tokenProvider;

  @Test
  void sampleRedisTemplateTest() {
    redisTemplate.opsForValue().set("key", "value");
    Boolean hasKey = redisTemplate.hasKey("key");

    assertThat(hasKey).isNotNull();
    assertThat(hasKey).isTrue();
  }

  @Test
  @DisplayName("만료된 RefreshToken 으로 요청 - 실패")
  void reIssueAccessToken() throws InterruptedException {
    // given
    final String refreshToken = issueRefreshToken(1L);
    Thread.sleep(3000);
    given()
          .log()
          .all()
          .contentType(ContentType.JSON)

    // when
    .when()
      .get("/auth/issue/access-token?refresh_token={refreshToken}", refreshToken)

    // then
    .then()
      .log().all()
      .assertThat().statusCode(401)
      .assertThat().body("error.code", is("A003"))
      .assertThat().body("error.message", is("유효하지 않은 토큰 값입니다. 다시 로그인 바랍니다."));
  }

  @Test
  @DisplayName("refreshToken 으로 accessToken 재발급 요청 - 성공")
  void successReIssueAccessToken() {
    // given
    final String refreshToken = issueRefreshToken(1L);
    saveRefreshToken(refreshToken);
    String accessToken = given()
          .log()
          .all()
          .contentType(ContentType.JSON)

    // when
    .when()
      .get("/auth/issue/access-token?refresh_token={refreshToken}", refreshToken)

    // then
    .then()
      .log().all()
      .assertThat().statusCode(200)
      .assertThat().body("result", is("SUCCESS"))

    .extract()
      .path("data.accessToken"); // AccessToken 이 발급되었는 지 확인
    assertThat(tokenProvider.isValidToken(accessToken)).isTrue();
    assertThat(tokenProvider.parseUserId(accessToken)).isEqualTo(1L);
  }

  private String issueRefreshToken(long userId) {
    return tokenProvider.issueRefreshToken(userId);
  }

  private void saveRefreshToken(String refreshToken) {
    redisTemplate.opsForValue().set(refreshToken, "1", Duration.ofSeconds(2));
  }
}
