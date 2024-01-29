package potenday.app.oauth;

import org.springframework.stereotype.Component;

@Component
public class KakaoOAuthUri implements OAuthUri {

  private final KakaoProperties kakaoProperties;

  public KakaoOAuthUri(KakaoProperties kakaoProperties) {
    this.kakaoProperties = kakaoProperties;
  }

  @Override
  public String generateUri(String oauthProvider, String uuid) {
    return kakaoProperties.getOauthEndpointUri()
        + "?response_type=code"
        + "&client_id=" + kakaoProperties.getClientId()
        + "&redirect_uri=" + kakaoProperties.getTokenIssueUri()
        + "&prompt=select_account&state=" + uuid;
  }
}
