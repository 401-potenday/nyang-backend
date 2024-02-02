package potenday.app.domain.cat.comment;

import java.util.List;
import potenday.app.domain.user.User;

public record AddCatComment(
    long contentId,
    String commentDesc,
    List<String> commentImageUris
) {

  public CatComment toCommentWithOwner(User user) {
    return new CatComment(commentDesc, contentId, user.getId());
  }
}
