package potenday.app.oauth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import potenday.app.global.config.PropertiesConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class, classes = PropertiesConfig.class)
@ActiveProfiles("dev")
class OAuthUriTest {

  @Autowired
  private KakaoProperties kakaoProperties;

  @Test
  @DisplayName("Redirect URI 를 이용해 OAuth Endpoint URI 를 만든다.")
  void generateUri() {
    OAuthUri oAuthUri = new KakaoOAuthUri(kakaoProperties);
    String oauthEndpointUri = oAuthUri.generateUri("http://localhost:3000");

    assertThat(oauthEndpointUri).isNotNull();

  }
}
