package potenday.app.global.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("oauth.kakao")
public class KakaoProperties {
  private final String clientId;
  private final String clientSecret;
  private final String oauthEndpointUri;
  private final String tokenIssueUri;
  private final String oauthUserInfoUri;
  private final String responseType;

  public KakaoProperties(String clientId, String clientSecret, String oauthEndpointUri,
      String tokenIssueUri, String oauthUserInfoUri, String responseType) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.oauthEndpointUri = oauthEndpointUri;
    this.tokenIssueUri = tokenIssueUri;
    this.oauthUserInfoUri = oauthUserInfoUri;
    this.responseType = responseType;
  }
}