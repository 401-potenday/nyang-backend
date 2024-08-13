package potenday.app.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import potenday.app.api.auth.LogoutTokenRequest;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.auth.TokenProvider;
import potenday.app.global.error.AuthenticationException;
import potenday.app.global.error.ErrorCode;

@Service
@Slf4j
public class AppTokenService {

    private final TokenProvider tokenProvider;
    private final StringRedisTemplate redisTemplate;

    public AppTokenService(TokenProvider tokenProvider, StringRedisTemplate redisTemplate) {
      this.tokenProvider = tokenProvider;
      this.redisTemplate = redisTemplate;
    }

    public String reIssueAccessToken(String refreshToken) {
      boolean validToken = tokenProvider.isValidToken(refreshToken);
      if (!validToken) {
        throw new AuthenticationException(ErrorCode.A003);
      }

      Boolean hasKey = redisTemplate.hasKey(refreshToken);
      if (hasKey == null || !hasKey) {
        throw new AuthenticationException(ErrorCode.A003);
      }

      return tokenProvider.issueAccessTokenFromRefreshToken(refreshToken);
    }

  public void removeRefreshToken(AppUser appUser, LogoutTokenRequest logoutTokenRequest) {
    if (appUser.id() != tokenProvider.parseUserId(logoutTokenRequest.getRefreshToken())) {
      throw new AuthenticationException(ErrorCode.A003);
    }

    Boolean hasKey = redisTemplate.hasKey(logoutTokenRequest.getRefreshToken());
    if (hasKey == null || !hasKey) {
      log.info("not found refresh token : {}", logoutTokenRequest.getRefreshToken());
      return;
    }

    boolean validToken = tokenProvider.isValidToken(logoutTokenRequest.getRefreshToken());
    if (!validToken) {
      log.error("requested refresh token is expired: {}", logoutTokenRequest.getRefreshToken());
      return;
    }

    Boolean deleted = redisTemplate.delete(logoutTokenRequest.getRefreshToken());
    if (deleted == null || !deleted) {
      // not throw exception
      log.error("refresh token not delete: {}", logoutTokenRequest.getRefreshToken());
    }
  }
}
