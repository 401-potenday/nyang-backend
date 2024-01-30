package potenday.app.domain.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import potenday.app.global.exception.InvalidTokenException;

@Component
public class AuthenticationTokenService implements AuthenticationService {

  private final TokenProvider tokenProvider;

  public AuthenticationTokenService(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void checkAuthentication(HttpServletRequest request) {
    final String token = tokenProvider.parseTokenFromHeader(request);
    if (!tokenProvider.isValidToken(token)) {
      throw new InvalidTokenException();
    }
  }

  @Override
  public AppUser findUserByToken(String token) {
    return new AppUser(tokenProvider.parseUserId(token));
  }
}