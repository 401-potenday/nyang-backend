package potenday.app.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import potenday.app.global.property.KakaoProperties;
import potenday.app.global.property.NaverProperties;

@Configuration
@EnableConfigurationProperties({KakaoProperties.class, NaverProperties.class})
public class PropertiesConfig {
}