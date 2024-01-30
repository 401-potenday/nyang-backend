package potenday.app.domain.auth;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {

  void checkAuthentication(HttpServletRequest request);

  Object findUserByToken(String token);
}
