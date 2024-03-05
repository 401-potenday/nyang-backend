package potenday.app.domain.auth;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.stereotype.Service;
import potenday.app.api.auth.AccessRefreshTokenResponse;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserRepository;
import potenday.app.oauth.OAuthMember;


@Service
public class OAuthAuthenticationService {

  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;
  private final TokenRepository tokenRepository;

  public OAuthAuthenticationService(UserRepository userRepository,
      TokenProvider tokenProvider, TokenRepository tokenRepository) {
    this.userRepository = userRepository;
    this.tokenProvider = tokenProvider;
    this.tokenRepository = tokenRepository;
  }


  @Transactional
  public AccessRefreshTokenResponse generateAccessRefreshToken(OAuthMember oAuthMember, String oauthProvider) {
    User user = findUser(oAuthMember, oauthProvider);
    String accessToken = tokenProvider.issueAccessToken(user.getId());
    String refreshToken = tokenProvider.issueRefreshToken(user.getId());
    saveOrUpdateToken(oAuthMember, refreshToken);
    if (user.getNickname() == null) {
      return AccessRefreshTokenResponse.newUser(accessToken, refreshToken);
    }
    return AccessRefreshTokenResponse.of(accessToken, refreshToken, user.getNickname());
  }

  private void saveOrUpdateToken(OAuthMember oAuthMember, String refreshToken) {
    Optional<Token> token = tokenRepository.findToken(oAuthMember.oauthUid());
    if (token.isEmpty()) {
      tokenRepository.save(new Token(oAuthMember.oauthUid(), oAuthMember.refreshToken(), refreshToken));
    } else {
      token.get().updateRt(refreshToken);
    }
  }

  public User findUser(OAuthMember oAuthMember, String oauthProvider) {
    Optional<User> user = userRepository.findOauthUser(oAuthMember.oauthUid());
    if (user.isEmpty()) {
      return userRepository.save(oAuthMember.toUser(oauthProvider));
    }
    return user.get();
  }
}
