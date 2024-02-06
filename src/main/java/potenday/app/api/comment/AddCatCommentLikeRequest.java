package potenday.app.api.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potenday.app.domain.cat.commentlikes.AddCatCommentLike;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCatCommentLikeRequest {

  @NotNull(message = "D001")
  private Long commentId;

  public AddCatCommentLike toAddCatComment(long contentId) {
    return new AddCatCommentLike(commentId, contentId);
  }
}
