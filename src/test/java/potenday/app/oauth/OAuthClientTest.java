package potenday.app.oauth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import potenday.app.api.auth.TokenRequest;

@MockitoSettings
public class OAuthClientTest {

  @Mock
  OAuthClient oAuthClient;

  @Test
  @DisplayName("code 와 redirectUri 를 통해 OAuthClient 로 부터 사용자 정보를 가져온다")
  void test() {
    TokenRequest tokenRequest = new TokenRequest("code", "redirectUri");
    OAuthMember oAuthMember = new OAuthMember("oauth-uuid", null,null);

    given(oAuthClient.findOAuthMember(tokenRequest)).willReturn(oAuthMember);

    OAuthMember findOauthMember = oAuthClient.findOAuthMember(tokenRequest);

    assertThat(findOauthMember).isNotNull();
  }

  @Test
  @DisplayName("code 와 redirectUri 를 통해 OAuthClient 로 부터 사용자 정보를 가져온다")
  void throwException() {
    TokenRequest tokenRequest = new TokenRequest("code", "redirectUri");
    OAuthMember oAuthMember = new OAuthMember("oauth-uuid", null,null);

    given(oAuthClient.findOAuthMember(tokenRequest)).willReturn(oAuthMember);

    OAuthMember findOauthMember = oAuthClient.findOAuthMember(tokenRequest);

    assertThat(findOauthMember).isNotNull();
  }


  @Test
  @DisplayName("code 와 redirectUri 를 이용해서 OAuth Token 정보를 가져온다.")
  void getOauthToken() {
    OAuthToken oAuthToken = new OAuthToken();
    given(oAuthClient.getToken(anyString(), anyString())).willReturn(oAuthToken);

    OAuthToken oAuthClientToken = oAuthClient.getToken("code", "redirectUri");

    assertThat(oAuthClientToken).isNotNull();
  }
}
