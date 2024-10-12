package potenday.app.domain.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class, classes = TokenProperty.class)
@TestPropertySource(value = {"classpath:application.yaml"})
class TokenPropertyTest {

  @Autowired
  private TokenProperty tokenProperty;

  @Test
  void getProperty() {
    assertThat(tokenProperty.secret()).isEqualTo("potenday");
    assertThat(tokenProperty.accessTokenLifeTime()).isEqualTo(600);
    assertThat(tokenProperty.refreshTokenLifeTime()).isEqualTo(86400);
  }
}