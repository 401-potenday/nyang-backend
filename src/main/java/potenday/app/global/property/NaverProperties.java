package potenday.app.global.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("oauth.naver")
public class NaverProperties {

  private final String clientId;
  private final String clientSecret;
  private final String oauthEndpointUri;
  private final String tokenIssueUri;
  private final String oauthUserInfoUri;

  public NaverProperties(String clientId, String clientSecret, String oauthEndpointUri,
      String tokenIssueUri, String oauthUserInfoUri) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.oauthEndpointUri = oauthEndpointUri;
    this.tokenIssueUri = tokenIssueUri;
    this.oauthUserInfoUri = oauthUserInfoUri;
  }
}