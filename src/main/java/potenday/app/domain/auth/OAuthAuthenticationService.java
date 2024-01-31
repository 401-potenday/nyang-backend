package potenday.app.domain.auth;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potenday.app.api.auth.AccessRefreshTokenResponse;
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


  public AccessRefreshTokenResponse generateAccessRefreshToken(OAuthMember oAuthMember, String oauthProvider) {
    User user = findUser(oAuthMember, oauthProvider);
    String accessToken = tokenProvider.issueAccessToken(user.getId());
    String refreshToken = tokenProvider.issueRefreshToken(user.getId());
    if (user.getNickname() == null) {
      return AccessRefreshTokenResponse.newUser(accessToken, refreshToken);
    }
    return AccessRefreshTokenResponse.of(accessToken, refreshToken, user.getNickname());
  }

  @Transactional
  public User findUser(OAuthMember oAuthMember, String oauthProvider) {
    Optional<User> user = userRepository.findOauthUser(oAuthMember.oauthUid());
    if (!user.isPresent()) {
      return userRepository.save(oAuthMember.toUser(oauthProvider));
    }
    return user.get();
  }
}
