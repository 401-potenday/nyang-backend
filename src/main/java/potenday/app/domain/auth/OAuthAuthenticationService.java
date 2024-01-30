package potenday.app.domain.auth;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potenday.app.api.auth.LoginResponse;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserRepository;
import potenday.app.oauth.OAuthMember;


@Service
public class OAuthAuthenticationService {

  private final TokenRepository tokenRepository;
  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;

  public OAuthAuthenticationService(TokenRepository tokenRepository, UserRepository userRepository,
      TokenProvider tokenProvider) {
    this.tokenRepository = tokenRepository;
    this.userRepository = userRepository;
    this.tokenProvider = tokenProvider;
  }

  public LoginResponse generateToken(OAuthMember oAuthMember, String oauthProvider) {
    User user = findUser(oAuthMember, oauthProvider);
    String accessToken = tokenProvider.issueAccessToken(user.getId());
    String refreshToken = tokenProvider.issueRefreshToken(user.getId());
    return new LoginResponse(accessToken, refreshToken, user.getoAuthUid());
  }

  @Transactional
  public User findUser(OAuthMember oAuthMember, String oauthProvider) {
    Optional<User> user = userRepository.findUser(String.valueOf(oAuthMember.getId()));
    if (!user.isPresent()) {
      return userRepository.save(oAuthMember.toUser(oauthProvider));
    }
    return user.get();
  }
}
