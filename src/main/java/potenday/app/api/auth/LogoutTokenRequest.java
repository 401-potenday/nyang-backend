package potenday.app.api.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogoutTokenRequest {

  @NotNull(message = "L005")
  @NotEmpty(message = "L005")
  private String refreshToken;
}
