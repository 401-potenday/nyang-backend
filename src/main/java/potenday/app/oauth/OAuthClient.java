package potenday.app.oauth;

import potenday.app.api.auth.TokenRequest;

public interface OAuthClient {

  OAuthMember findOAuthMember(TokenRequest tokenRequest);

  OAuthToken getToken(String code, String redirectUri);

  OAuthUserId unlink(String accessToken);
}
