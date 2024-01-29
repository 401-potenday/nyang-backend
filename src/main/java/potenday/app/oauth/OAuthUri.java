package potenday.app.oauth;

public interface OAuthUri {

  String generateUri(String oauthProvider, String uuid);
}
