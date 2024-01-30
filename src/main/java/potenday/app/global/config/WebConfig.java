package potenday.app.global.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import potenday.app.domain.auth.AuthenticationInterceptor;
import potenday.app.domain.auth.AuthenticationPrincipalArgumentResolver;
import potenday.app.domain.auth.AuthenticationTokenService;
import potenday.app.domain.auth.TokenProvider;

@Configuration
@Profile("dev")
public class WebConfig implements WebMvcConfigurer {

  private final AuthenticationTokenService authenticationTokenService;
  private final TokenProvider tokenProvider;

  public WebConfig(AuthenticationTokenService authenticationTokenService,
      TokenProvider tokenProvider) {
    this.authenticationTokenService = authenticationTokenService;
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedMethods("*")
        .allowedOriginPatterns("http://localhost:3000");
  }

  @Bean
  public AuthenticationInterceptor authenticationInterceptor() {
    return new AuthenticationInterceptor(authenticationTokenService);
  }

  @Bean
  public HandlerMethodArgumentResolver authenticationPrincipalArgumentResolver() {
    return new AuthenticationPrincipalArgumentResolver(authenticationTokenService, tokenProvider);
  }

  // register interceptor
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authenticationInterceptor())
        .excludePathPatterns(
            "/auth/kakao/signin/**",
            "/auth/kakao/token/**",
            "/user/nickname/available-check/**"
        );
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(authenticationPrincipalArgumentResolver());
  }
}
