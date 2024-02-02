package potenday.app.api.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potenday.app.domain.cat.commentlikes.CancelCommentLike;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CancelCommentLikeRequest {

  @NotNull(message = "D001")
  private Long commentId;

  public CancelCommentLike toCancelComment(long contentId) {
    return new CancelCommentLike(commentId, contentId);
  }
}
