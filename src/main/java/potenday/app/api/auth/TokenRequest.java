package potenday.app.api.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenRequest {

  @NotNull(message = "L003")
  @NotEmpty(message = "L003")
  private String code;

  @NotNull(message = "L004")
  @NotEmpty(message = "L004")
  private String redirectUri;
}
