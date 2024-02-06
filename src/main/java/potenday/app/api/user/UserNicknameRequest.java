package potenday.app.api.user;

import static potenday.app.api.validation.ValidationGroups.NotBlankGroup;
import static potenday.app.api.validation.ValidationGroups.NotWhiteSpaceGroup;
import static potenday.app.api.validation.ValidationGroups.SizeGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserNicknameRequest {

  @NotBlank(message = "U004", groups = NotBlankGroup.class)
  @Pattern(regexp = "\\S+", message = "U003", groups = NotWhiteSpaceGroup.class)
  @Size(min = 3, max = 15, message = "U002", groups = SizeGroup.class)
  private String nickname;

  public String toNickname() {
    return nickname;
  }
}
