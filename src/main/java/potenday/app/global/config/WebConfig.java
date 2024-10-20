package potenday.app.global.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpMethod;
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
import potenday.app.global.interceptor.RequestLoggingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final AuthenticationTokenService authenticationTokenService;
  private final RequestLoggingInterceptor requestLoggingInterceptor;
  private final TokenProvider tokenProvider;

  public WebConfig(AuthenticationTokenService authenticationTokenService,
      RequestLoggingInterceptor requestLoggingInterceptor,
      TokenProvider tokenProvider) {
    this.authenticationTokenService = authenticationTokenService;
    this.requestLoggingInterceptor = requestLoggingInterceptor;
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedHeaders("*")
        .allowedMethods(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.DELETE.name(),
            HttpMethod.PUT.name(),
            HttpMethod.OPTIONS.name()
        )
        .allowCredentials(true)
        .allowedOriginPatterns(
            "http://localhost:3000",
            "https://it-that-cat.vercel.app",
            "https://nyangnyang.co.kr",
            "https://www.nyangnyang.co.kr",
            "https://dev.itthatcat.xyz"
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
    registry.addInterceptor(requestLoggingInterceptor)
        .order(1)
        .addPathPatterns("/**");

    registry.addInterceptor(authenticationInterceptor())
        .order(2)
        .excludePathPatterns(
            "/auth/**/oauth-uri",
            "/contents",
            "/contents/**",
            "/contents/follow/**",
            "/contents/**/comments/**",
            "/auth/**/token",
            "/auth/issue/**",
            "/auth/user",
            "/user/nickname/available-check/**",
            "/addr"
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
