package potenday.app.oauth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import potenday.app.domain.user.User;

class OAuthMemberTest {

  @Test
  @DisplayName("OAuthMember 에서 User 객체로 변환 - 성공")
  void toUser() {
    OAuthMember oAuthMember = new OAuthMember("oauth-uuid",null);
    User user = oAuthMember.toUser("kakao");

    assertThat(user).isNotNull();
    assertThat(user.getNickname()).isNull();
    assertThat(user.getoAuthUid()).isEqualTo("oauth-uuid");
  }

  @Test
  @DisplayName("Oauth 고유 id 를 이용하여 OAuth 객체를 만든다. - 성공")
  void from() {
    OAuthMember oAuthMember = OAuthMember.from("oauth-uuid", null);

    assertThat(oAuthMember.oauthUid()).isEqualTo("oauth-uuid");
  }
}