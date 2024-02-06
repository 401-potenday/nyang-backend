package potenday.app.oauth;

import potenday.app.domain.user.User;
import potenday.app.domain.user.UserOAuthProvider;

public record OAuthMember(String oauthUid) {

  public User toUser(final String userOAuthProvider) {
    return User.builder()
        .oAuthUid(oauthUid)
        .userOAuthProvider(UserOAuthProvider.from(userOAuthProvider))
        .build();
  }

  public static OAuthMember from(final String oauthUid) {
    return new OAuthMember(oauthUid);
  }
}
