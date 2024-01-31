package potenday.app.api.auth;

public record OAuthUriResponse(
    OAuthProvider provider,
    String oauthUri
) {

  public static OAuthUriResponse of(String provider, String oauthUri) {
    return new OAuthUriResponse(OAuthProvider.of(provider), oauthUri);
  }
}
