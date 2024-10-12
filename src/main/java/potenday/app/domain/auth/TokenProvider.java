package potenday.app.domain.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import potenday.app.global.error.AuthenticationException;
import potenday.app.global.error.ErrorCode;

@Component
public class TokenProvider {

  private static final String UID = "uid";
  private static final String TOKEN_TYPE = "token_type";
  private static final String EXP = "exp";
  private static final String IAT = "iat";

  public String issueAccessTokenFromRefreshToken(String refreshToken) {
    Long userId = JWT.decode(refreshToken).getClaim(UID).asLong();
    return issueAccessToken(userId);
  }

  public enum TokenType {
    ACCESS,
    REFRESH;

    public final static List<TokenType> tokens = Arrays.stream(TokenType.values()).toList();

    public static TokenType of(String type) {
      return tokens.stream()
          .filter(it -> it.name().equals(type))
          .findFirst()
          .orElseThrow();
    }
  }

  private final Algorithm AL;
  private final TokenProperty jwtProperties;

  public TokenProvider(TokenProperty jwtProperties) {
    this.jwtProperties = jwtProperties;
    this.AL = Algorithm.HMAC512(jwtProperties.secret());
  }

  private String generate(long userId, TokenType tokenType) {
    long now = Instant.now().getEpochSecond();
    return JWT.create()
        .withClaim(UID, userId)
        .withClaim(TOKEN_TYPE, tokenType.name())
        .withClaim(IAT, now)
        .withClaim(EXP, now + getLifeTime(tokenType))
        .sign(AL);
  }

  public String issueAccessToken(long userId) {
    return generate(userId, TokenType.ACCESS);
  }

  public String issueRefreshToken(long userId) {
    return generate(userId, TokenType.REFRESH);
  }

  public boolean isValidToken(String token) {
    try {
      JWTVerifier verifier = JWT.require(AL)
          .build();
      verifier.verify(token);
      return true;
    } catch (JWTVerificationException exception){
      return false;
    }
  }

  public long parseUserId(String token) {
    return JWT.decode(token).getClaim(UID).asLong();
  }

  private long getLifeTime(TokenType tokenType) {
    return switch (tokenType) {
      case ACCESS -> this.jwtProperties.accessTokenLifeTime();
      default -> this.jwtProperties.refreshTokenLifeTime();
    };
  }

  public String parseTokenFromHeader(HttpServletRequest httpServletRequest) {
    final String authorization = httpServletRequest.getHeader("Authorization");
    if (Objects.isNull(authorization) || !authorization.startsWith("Bearer")) {
      throw new AuthenticationException(ErrorCode.A002);
    }
    return authorization.substring(7);
  }
}
