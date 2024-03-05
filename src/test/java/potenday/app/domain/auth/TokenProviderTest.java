package potenday.app.domain.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import potenday.app.domain.auth.TokenProvider.TokenType;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {TokenProperty.class, TokenProvider.class}
)
@TestPropertySource(
    locations = "classpath:application.yaml",
    properties = {
        "jwt.access-time-sec = 1",
        "jwt.refresh-time-sec = 2"
    }
)
class TokenProviderTest {

  private final long userId = 1;

  @Autowired
  TokenProperty tokenProperty;

  @Autowired
  TokenProvider tokenProvider;

  @Test
  @DisplayName("TokenProvider 를 이용해 AccessToken 을 발행한다.")
  void issueAccessToken() {


    // When
    String accessToken = tokenProvider.issueAccessToken(userId);

    DecodedJWT jwt = JWT.decode(accessToken);
    long userId = jwt.getClaim("uid").asLong();
    String type = jwt.getClaim("token_type").asString();

    assertThat(userId).isEqualTo(this.userId);
    assertThat(TokenType.of(type)).isEqualTo(TokenType.ACCESS);
  }

  @Test
  @DisplayName("TokenProvider 를 이용해 RefreshToken 을 발행한다.")
  void issueRefreshToken() {
    // When
    String refreshToken = tokenProvider.issueRefreshToken(userId);

    // Then
    DecodedJWT jwt = JWT.decode(refreshToken);
    long userId = jwt.getClaim("uid").asLong();
    String type = jwt.getClaim("token_type").asString();

    assertThat(userId).isEqualTo(this.userId);
    assertThat(TokenType.of(type)).isEqualTo(TokenType.REFRESH);
  }

  @Test
  @DisplayName("발급된 AccessToken 은 RefreshToken 보다 만료시간이 더 짦다.")
  void accessTokenHasShorterExpirationTime() {
    // When
    String refreshToken = tokenProvider.issueRefreshToken(userId);
    String accessToken = tokenProvider.issueAccessToken(userId);
    DecodedJWT decodeAccess = JWT.decode(accessToken);
    DecodedJWT decodeRefresh = JWT.decode(refreshToken);

    Instant accessTokenInstant = decodeAccess.getClaim("exp").asInstant();
    Instant refreshTokenInstant = decodeRefresh.getClaim("exp").asInstant();

    // Then
    assertThat(accessTokenInstant).isBefore(refreshTokenInstant);
  }

  @Test
  @DisplayName("TokenLifeTime 시간이 지나기 전까지는 발행한 AccessToken 은 만료되지 않는다.")
  void accessTokenNowExpiresWithInTokenLifeTime() throws InterruptedException {
    // When
    String accessToken = tokenProvider.issueAccessToken(userId);

    // 1초후
    Thread.sleep(10);

    DecodedJWT decodedJWT = JWT.decode(accessToken);
    Date expiresAt = decodedJWT.getExpiresAt();

    assertThat(expiresAt.before(new Date())).isFalse();
  }

  @Test
  @DisplayName("TokenLifeTime 이후 발행한 AccessToken 은 만료된다.")
  void accessTokenExpiresAfterTokenLifeTime() throws InterruptedException {
    // When
    String accessToken = tokenProvider.issueAccessToken(userId);

    // 1초후
    Thread.sleep(3000);

    DecodedJWT decodedJWT = JWT.decode(accessToken);
    Date expiresAt = decodedJWT.getExpiresAt();

    assertThat(expiresAt.before(new Date())).isTrue();
  }

  @Test
  @DisplayName("정상적인 토큰의 경우 유효성을 검사 시 True 반환")
  void throwExceptionNotInvalidToken() {
    String accessToken = tokenProvider.issueAccessToken(userId);
    assertThat(tokenProvider.isValidToken(accessToken)).isTrue();
  }

  @Test
  @DisplayName("유효한 토큰의 경우 예외를 던지지 않는다.")
  void notThrowExceptionWhenValidToken() {
    String accessToken = tokenProvider.issueAccessToken(userId);
    assertThatCode(() -> tokenProvider.isValidToken(accessToken))
        .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Token 으로부터 'uid' Claim 을 가져온다.")
  void getUserSeqFromToken() {
    String accessToken = tokenProvider.issueAccessToken(userId);
    long parsedUserId = tokenProvider.parseUserId(accessToken);
    assertThat(parsedUserId).isEqualTo(userId);
  }

  @Test
  @DisplayName("요청 헤더 에서 Token 을 파싱한다.")
  void parseTokenFromHeader() {
    String accessToken = tokenProvider.issueAccessToken(userId);

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "Bearer " + accessToken);

    String parsedToken = tokenProvider.parseTokenFromHeader(request);

    assertThat(parsedToken).isEqualTo(accessToken);
  }
}