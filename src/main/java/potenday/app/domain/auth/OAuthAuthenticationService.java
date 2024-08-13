package potenday.app.domain.auth;

import jakarta.transaction.Transactional;
import java.time.Duration;
import java.util.Optional;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import potenday.app.api.auth.AccessRefreshTokenResponse;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserRepository;
import potenday.app.oauth.OAuthMember;


@Service
public class OAuthAuthenticationService {

  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;
  private final StringRedisTemplate redisTemplate;
  private final TokenProperty tokenProperty;

  public OAuthAuthenticationService(UserRepository userRepository,
      TokenProvider tokenProvider, TokenProperty tokenProperty, StringRedisTemplate redisTemplate) {
    this.userRepository = userRepository;
    this.tokenProvider = tokenProvider;
    this.tokenProperty = tokenProperty;
    this.redisTemplate = redisTemplate;
  }


  @Transactional
  public AccessRefreshTokenResponse generateAccessRefreshToken(OAuthMember oAuthMember, String oauthProvider) {
    User user = findUser(oAuthMember, oauthProvider);
    String accessToken = tokenProvider.issueAccessToken(user.getId());
    String refreshToken = tokenProvider.issueRefreshToken(user.getId());
    saveRefreshToken(refreshToken);
    if (user.getNickname() == null) {
      return AccessRefreshTokenResponse.newUser(accessToken, refreshToken);
    }
    return AccessRefreshTokenResponse.of(accessToken, refreshToken, user.getNickname());
  }

  private void saveRefreshToken(String refreshToken) {
    redisTemplate.opsForValue()
        .set(refreshToken, "1", Duration.ofSeconds(tokenProperty.refreshTokenLifeTime()));
  }

  public User findUser(OAuthMember oAuthMember, String oauthProvider) {
    Optional<User> user = userRepository.findOauthUser(oAuthMember.oauthUid());
    if (user.isEmpty()) {
      return userRepository.save(oAuthMember.toUser(oauthProvider));
    }
    return user.get();
  }
}
