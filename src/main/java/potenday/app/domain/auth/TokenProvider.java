package potenday.app.domain.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

  private static final String UID = "uid";
  private static final String TOKEN_TYPE = "token_type";
  private static final String EXP = "exp";
  private static final String IAT = "iat";

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
    this.AL = Algorithm.HMAC512(jwtProperties.getSecret());
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

  public long parseUserId(String token) {
    return JWT.decode(token).getClaim(UID).asLong();
  }

  private long getLifeTime(TokenType tokenType) {
    return switch (tokenType) {
      case ACCESS -> this.jwtProperties.getTokenLifeTime();
      default -> this.jwtProperties.getTokenRefreshTime();
    };
  }
}