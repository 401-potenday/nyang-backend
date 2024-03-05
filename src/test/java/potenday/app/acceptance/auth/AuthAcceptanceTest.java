package potenday.app.acceptance.auth;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import potenday.app.acceptance.AcceptanceTest;

@DisplayName("인증 인수테스트")
public class AuthAcceptanceTest extends AcceptanceTest {

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
}
