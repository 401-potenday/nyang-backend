package potenday.app.oauth;

import potenday.app.api.auth.TokenRequest;

public interface OAuthClient {

  OAuthMember findOAuthMember(TokenRequest tokenRequest);

  OAuthTokenResponse requestKakaoToken(String code);

  OAuthMember requestKakaoMember(String accessToken);
}
