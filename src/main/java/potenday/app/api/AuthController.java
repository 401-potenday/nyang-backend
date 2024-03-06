package potenday.app.api;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import potenday.app.api.auth.AccessRefreshTokenResponse;
import potenday.app.api.auth.AccessTokenResponse;
import potenday.app.api.auth.OAuthUriResponse;
import potenday.app.api.auth.TokenRequest;
import potenday.app.api.common.ApiResponse;
import potenday.app.domain.auth.OAuthAuthenticationService;
import potenday.app.oauth.OAuthClient;
import potenday.app.oauth.OAuthMember;
import potenday.app.oauth.OAuthUri;

@RestController
@Slf4j
public class AuthController {

  private final OAuthUri oAuthUri;
  private final OAuthClient oAuthClient;
  private final OAuthAuthenticationService oAuthAuthenticationService;

  public AuthController(OAuthUri oAuthUri, OAuthClient oAuthClient,
      OAuthAuthenticationService oAuthAuthenticationService) {
    this.oAuthUri = oAuthUri;
    this.oAuthClient = oAuthClient;
    this.oAuthAuthenticationService = oAuthAuthenticationService;
  }

  @GetMapping("/auth/{oAuthProvider}/oauth-uri")
  public ApiResponse<OAuthUriResponse> redirect(
      @PathVariable String oAuthProvider,
      @RequestParam(value = "redirect_uri") String redirectUri
  ) {
    return ApiResponse.success(OAuthUriResponse.of(oAuthProvider, oAuthUri.generateUri(redirectUri)));
  }

  @PostMapping("/auth/{oAuthProvider}/token")
  public ApiResponse<AccessRefreshTokenResponse> generateAccessRefreshToken(
      @Valid @RequestBody TokenRequest tokenRequest,
      @PathVariable String oAuthProvider
  ) {
    // OAuth provider 에게 유저 정보 요청
    OAuthMember oAuthMember = oAuthClient.findOAuthMember(tokenRequest);

    // 토큰 발급
    AccessRefreshTokenResponse tokenResponse = oAuthAuthenticationService
        .generateAccessRefreshToken(oAuthMember, oAuthProvider);
    return ApiResponse.success(tokenResponse);
  }
}
