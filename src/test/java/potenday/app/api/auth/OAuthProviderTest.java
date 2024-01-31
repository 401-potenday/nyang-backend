package potenday.app.api.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import potenday.app.global.error.PotendayException;

class OAuthProviderTest {

  @Test
  @DisplayName("지원하는 provider 를 반환한다 - 성공")
  void of() {
    OAuthProvider providerKakao = OAuthProvider.of("kakao");

    assertThat(providerKakao).isEqualTo(OAuthProvider.KAKAO);
  }

  @Test
  @DisplayName("지원하지 않는 provider 는 예외를 던진다. - 실패")
  void throwExceptionGivenNotSupportProvider() {
    assertThatThrownBy(() -> OAuthProvider.of("google"))
        .isExactlyInstanceOf(PotendayException.class);
  }
}