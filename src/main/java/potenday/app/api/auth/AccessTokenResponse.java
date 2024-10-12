package potenday.app.api.auth;

public record AccessTokenResponse(String accessToken) {

  public static AccessTokenResponse from(String accessToken) {
    return new AccessTokenResponse(accessToken);
  }
}
