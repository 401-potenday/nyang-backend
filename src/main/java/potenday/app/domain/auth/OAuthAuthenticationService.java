package potenday.app.domain.auth;

import static potenday.app.global.error.ErrorCode.A001;
import static potenday.app.global.error.ErrorCode.L006;

import jakarta.transaction.Transactional;
import java.time.Duration;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import potenday.app.api.auth.AccessRefreshTokenResponse;
import potenday.app.api.auth.OAuthProvider;
import potenday.app.api.auth.TokenRequest;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserRepository;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;
import potenday.app.oauth.OAuthClient;
import potenday.app.oauth.OAuthMember;
import potenday.app.oauth.OAuthUserId;


@Service
@Slf4j
public class OAuthAuthenticationService {

  private final UserRepository userRepository;
  private final TokenProvider tokenProvider;
  private final StringRedisTemplate redisTemplate;
  private final TokenProperty tokenProperty;
  private final OAuthClient oAuthClient;

  public OAuthAuthenticationService(UserRepository userRepository,
      TokenProvider tokenProvider, TokenProperty tokenProperty, StringRedisTemplate redisTemplate,
      OAuthClient oAuthClient) {
    this.userRepository = userRepository;
    this.tokenProvider = tokenProvider;
    this.tokenProperty = tokenProperty;
    this.redisTemplate = redisTemplate;
    this.oAuthClient = oAuthClient;
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

  @Transactional
  public void deleteAccount(AppUser appUser, TokenRequest tokenRequest) {
    User user = userRepository.findAppUser(appUser.id()).orElseThrow(() -> new PotendayException(L006));
    user.markDelete();
    OAuthMember oAuthMember = oAuthClient.findOAuthMember(tokenRequest);
    oAuthClient.unlink(oAuthMember.accessToken());
  }
}
