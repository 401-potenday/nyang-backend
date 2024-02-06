package potenday.app.api.content;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potenday.app.domain.cat.follow.addFollow;
import potenday.app.domain.cat.follow.UnFollow;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatFollowAndCancelRequest {

  @NotNull(message = "F002")
  private Long contentId;

  public addFollow toAddFollow() {
    return new addFollow(contentId);
  }

  public UnFollow toCancelFollow() {
    return new UnFollow(contentId);
  }
}
