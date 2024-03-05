package potenday.app.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {
 *  "access_token":"[access_token]",
 *  "token_type":"bearer",
 *  "refresh_token":"[refresh_token]",
 *  "expires_in":21599,
 *  "scope":"account_email, profile_nickname",
 *  "refresh_token_expires_in":5183999
 *  }
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OAuthToken implements Serializable {

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("expires_in")
  private Integer accessExpiresIn;

  @JsonProperty("refresh_token_expires_in")
  private Integer refreshExpiresIn;
}
