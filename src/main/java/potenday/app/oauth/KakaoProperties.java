package potenday.app.oauth;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Getter
@ConfigurationProperties("oauth.kakao")
@Profile({"dev", "local"})
public class KakaoProperties {
  private final String clientId;
  private final String clientSecret;
  private final String oauthEndpointUri;
  private final String oauthTokenIssueUri;
  private final String oauthUserInfoUri;
  private final String oauthUnlinkUri;
  private final String responseType;

  public KakaoProperties(String clientId, String clientSecret, String oauthEndpointUri, String oauthTokenIssueUri, String oauthUserInfoUri,
      String oauthUnlinkUri, String responseType) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.oauthEndpointUri = oauthEndpointUri;
    this.oauthTokenIssueUri = oauthTokenIssueUri;
    this.oauthUserInfoUri = oauthUserInfoUri;
    this.oauthUnlinkUri = oauthUnlinkUri;
    this.responseType = responseType;
  }
}