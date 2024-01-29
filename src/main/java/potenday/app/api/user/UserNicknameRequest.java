package potenday.app.api.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserNicknameRequest {

  @NotBlank(message = "U004")
  @Size(min = 3, max = 15, message = "U002")
  @Pattern(regexp = "\\S+", message = "U003")
  @Pattern(regexp = "^[가-힣a-zA-Z]*$", message = "U005")
  private String nickname;
}
