package potenday.app.query.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserNickname {
  private Long userId;
  private String nickname;
}
