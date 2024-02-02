package potenday.app.api.content;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potenday.app.domain.cat.follow.AddCatFollow;
import potenday.app.domain.cat.follow.CancelCatFollow;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatFollowAndCancelRequest {

  @NotNull(message = "F002")
  private Long contentId;

  public AddCatFollow toAddFollow() {
    return new AddCatFollow(contentId);
  }

  public CancelCatFollow toCancelFollow() {
    return new CancelCatFollow(contentId);
  }
}
