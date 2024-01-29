package potenday.app.oauth;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Getter
@ConfigurationProperties("oauth.kakao")
@Profile("dev")
public class KakaoProperties {
  private final String clientId;
  private final String clientSecret;
  private final String oauthEndpointUri;
  private final String tokenIssueUri;
  private final String oauthTokenIssueUri;
  private final String oauthUserInfoUri;
  private final String responseType;

  public KakaoProperties(String clientId, String clientSecret, String oauthEndpointUri,
      String tokenIssueUri, String oauthTokenIssueUri, String oauthUserInfoUri, String responseType) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.oauthEndpointUri = oauthEndpointUri;
    this.tokenIssueUri = tokenIssueUri;
    this.oauthTokenIssueUri = oauthTokenIssueUri;
    this.oauthUserInfoUri = oauthUserInfoUri;
    this.responseType = responseType;
  }
}