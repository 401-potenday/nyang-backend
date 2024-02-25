package potenday.app.global.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import potenday.app.domain.auth.AuthenticationInterceptor;
import potenday.app.domain.auth.AuthenticationPrincipalArgumentResolver;
import potenday.app.domain.auth.AuthenticationTokenService;
import potenday.app.domain.auth.OptionalAuthenticationPrincipalArgumentResolver;
import potenday.app.domain.auth.TokenProvider;
import potenday.app.global.converter.CreateTimeOrderConverter;
import potenday.app.global.converter.DistanceOrderConverter;

@Configuration
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
        .allowedOriginPatterns(
            "http://localhost:3000",
            "https://it-that-cat.vercel.app",
            "https:/dev.itthatcat.xyz"
        );
  }

  @Bean
  public AuthenticationInterceptor authenticationInterceptor() {
    return new AuthenticationInterceptor(authenticationTokenService);
  }

  @Bean
  public HandlerMethodArgumentResolver authenticationPrincipalArgumentResolver() {
    return new AuthenticationPrincipalArgumentResolver(authenticationTokenService, tokenProvider);
  }

  @Bean
  public OptionalAuthenticationPrincipalArgumentResolver optionalAuthenticationPrincipalArgumentResolver() {
    return new OptionalAuthenticationPrincipalArgumentResolver(authenticationTokenService);
  }

  // register interceptor
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authenticationInterceptor())
        .excludePathPatterns(
            "/auth/**/oauth-uri",
            "/contents",
            "/contents/**",
            "/contents/follow/**",
            "/contents/**/comments/**",
            "/auth/**/token",
            "/user/nickname/available-check/**"
        );
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(authenticationPrincipalArgumentResolver());
    resolvers.add(optionalAuthenticationPrincipalArgumentResolver());
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new CreateTimeOrderConverter());
    registry.addConverter(new DistanceOrderConverter());
  }
}
