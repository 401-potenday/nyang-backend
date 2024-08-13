package potenday.app.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.redis.core.StringRedisTemplate;
import potenday.app.domain.auth.TokenProvider;
import potenday.app.global.error.AuthenticationException;

@MockitoSettings
class AppTokenServiceTest {

  @Mock
  private StringRedisTemplate redisTemplate;

  @Mock
  private TokenProvider tokenProvider;

  @InjectMocks
  private AppTokenService appTokenService;

  @Test
  @DisplayName("RefreshToken 을 이용하여 AccessToken 을 발급한다. - 성공")
  void reissueAccessToken() {
    String refreshToken = "refreshTokenSample";
    given(tokenProvider.isValidToken(refreshToken)).willReturn(true);
    given(tokenProvider.issueAccessTokenFromRefreshToken(refreshToken)).willReturn(
        "new-access-token");
    given(redisTemplate.hasKey(refreshToken)).willReturn(true);

    // when
    String accessToken = appTokenService.reIssueAccessToken(refreshToken);

    // then
    assertThat(accessToken).isNotNull();
    assertThat(accessToken).isEqualTo("new-access-token");
  }

  @Test
  @DisplayName("유효하지 않은 RefreshToken 이 올 경우 에외를 던진다. - 실패")
  void throwExceptionNotValidToken() {
    // given
    String refreshToken = "refreshTokenSample";
    given(tokenProvider.isValidToken(refreshToken)).willReturn(false);

    // when, then
    assertThatThrownBy(() -> appTokenService.reIssueAccessToken(refreshToken))
        .isInstanceOf(AuthenticationException.class);
  }

  @Test
  @DisplayName("저장된 RefreshToken 가 존재하지 않을 경우 올 경우 에외를 던진다. - 실패")
  void throwExceptionNotExistedToken() {
    // given
    String refreshToken = "refreshTokenSample";
    given(tokenProvider.isValidToken(refreshToken)).willReturn(true);
    given(redisTemplate.hasKey(refreshToken)).willReturn(false);

    // when, then
    assertThatThrownBy(() -> appTokenService.reIssueAccessToken(refreshToken))
        .isInstanceOf(AuthenticationException.class);
  }
}