package potenday.app.api.auth;

public record AccessRefreshTokenResponse(String accessToken, String refreshToken, String nickname) {

  public static AccessRefreshTokenResponse of(String accessToken, String refreshToken, String nickname) {
    return new AccessRefreshTokenResponse(accessToken, refreshToken, nickname);
  }

  public static AccessRefreshTokenResponse newUser(String accessToken, String refreshToken) {
    return new AccessRefreshTokenResponse(accessToken, refreshToken, null);
  }
}
