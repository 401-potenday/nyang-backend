package potenday.app.oauth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserOAuthProvider;

@Setter
@Getter
@ToString
public class OAuthMember {

  private long id;
  private String nickname;

  public User toUser(String userOAuthProvider) {
    return User.builder()
        .oAuthUid(String.valueOf(id))
        .nickname(nickname)
        .userOAuthProvider(UserOAuthProvider.from(userOAuthProvider))
        .build();
  }

  @Builder
  public OAuthMember(final long id, final String nickname) {
    this.id = id;
    this.nickname = nickname;
  }
}
