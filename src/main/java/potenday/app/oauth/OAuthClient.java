package potenday.app.oauth;

import potenday.app.api.auth.TokenRequest;

public interface OAuthClient {

  OAuthMember findOAuthMember(TokenRequest tokenRequest);

  OAuthTokenResponse getToken(String code, String redirectUri);

}
