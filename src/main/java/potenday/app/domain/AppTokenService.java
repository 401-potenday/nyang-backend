package potenday.app.domain;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import potenday.app.domain.auth.TokenProvider;
import potenday.app.global.error.AuthenticationException;
import potenday.app.global.error.ErrorCode;

@Service
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

}
