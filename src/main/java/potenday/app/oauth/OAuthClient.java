package potenday.app.oauth;

import potenday.app.api.auth.TokenRequest;

public interface OAuthClient {

  OAuthMember findOAuthMember(TokenRequest tokenRequest);

  OAuthTokenResponse getOAuthToken(String code);

  OAuthMember getOAuthMember(String accessToken);
}
