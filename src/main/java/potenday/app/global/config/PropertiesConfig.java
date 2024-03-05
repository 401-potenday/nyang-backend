package potenday.app.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import potenday.app.oauth.KakaoProperties;

@Configuration
@EnableConfigurationProperties({KakaoProperties.class})
public class PropertiesConfig {
}