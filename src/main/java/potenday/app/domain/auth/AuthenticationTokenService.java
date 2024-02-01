package potenday.app.domain.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import potenday.app.global.error.AuthenticationException;
import potenday.app.global.error.ErrorCode;

@Component
@Slf4j
public class AuthenticationTokenService implements AuthenticationService {

  private final TokenProvider tokenProvider;

  public AuthenticationTokenService(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void checkAuthentication(HttpServletRequest request) {
    final String token = tokenProvider.parseTokenFromHeader(request);
    if (!tokenProvider.isValidToken(token)) {
      log.warn("Invalid JWT token: {}, request: {}", token, request);
      throw new AuthenticationException(ErrorCode.A003);
    }
  }

  @Override
  public AppUser findUserByToken(String token) {
    return new AppUser(tokenProvider.parseUserId(token));
  }
}