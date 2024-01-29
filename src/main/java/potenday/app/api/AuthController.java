package potenday.app.api;

import java.net.URI;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import potenday.app.api.auth.LoginResponse;
import potenday.app.api.auth.TokenRequest;
import potenday.app.api.common.ApiResponse;
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
  public ApiResponse<LoginResponse> generateAccessRefreshToken(
      @PathVariable String oauthProvider,
      final TokenRequest tokenRequest
  ) {
    // 1. OAuth 로 부터 유저정보 가져오기
    OAuthMember oAuthMember = oAuthClient.findOAuthMember(tokenRequest);

    // 2. 토큰 생성
    LoginResponse loginResponse = authenticationService.generateToken(oAuthMember, oauthProvider);
    return ApiResponse.success(loginResponse);
  }
}
