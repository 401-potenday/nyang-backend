package potenday.app.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import potenday.app.api.auth.LoginResponse;
import potenday.app.api.auth.TokenRequest;
import potenday.app.domain.auth.AuthenticationService;
import potenday.app.oauth.OAuthClient;
import potenday.app.oauth.OAuthMember;
import potenday.app.oauth.OAuthUri;

@RestController
@Slf4j
public class AuthController {

  private final OAuthUri oAuthUri;
  private final OAuthClient oAuthClient;
  private final AuthenticationService authenticationService;

  @Value("${client.redirect-url}")
  private String recirectUri;

  public AuthController(OAuthUri oAuthUri, OAuthClient oAuthClient,
      AuthenticationService authenticationService) {
    this.oAuthUri = oAuthUri;
    this.oAuthClient = oAuthClient;
    this.authenticationService = authenticationService;
  }

  @GetMapping("/auth/{oAuthProvider}/signin")
  public ResponseEntity<Void> redirect(@PathVariable String oAuthProvider) {
    return ResponseEntity.status(HttpStatus.FOUND)
        .location(URI.create(oAuthUri.generateUri(oAuthProvider, UUID.randomUUID().toString())))
        .build();
  }

  @GetMapping("/auth/{oauthProvider}/token")
  public ResponseEntity<?> generateAccessRefreshToken(
      @PathVariable String oauthProvider,
      final TokenRequest tokenRequest,
      HttpServletResponse httpServletResponse
  ) {
    // 1. OAuth 로 부터 유저정보 가져오기
    OAuthMember oAuthMember = oAuthClient.findOAuthMember(tokenRequest);

    // 2. 토큰 생성
    LoginResponse loginResponse = authenticationService.generateToken(oAuthMember, oauthProvider);

    setCookie(httpServletResponse, loginResponse);
    return ResponseEntity.status(302)
        .location(URI.create(recirectUri))
        .build();
  }

  private void setCookie(HttpServletResponse response, LoginResponse loginResponse) {
    Cookie accessToken = new Cookie("accessToken", loginResponse.accessToken());
    accessToken.setPath("/");

    Cookie refreshToken = new Cookie("refreshToken", loginResponse.refreshToken());
    refreshToken.setPath("/");

    response.addCookie(accessToken);
    response.addCookie(refreshToken);
  }
}
