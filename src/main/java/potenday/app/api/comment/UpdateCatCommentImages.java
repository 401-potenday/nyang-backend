package potenday.app.api.comment;

import java.util.List;
import potenday.app.domain.cat.comment.CatCommentImages;

public record UpdateCatCommentImages(
    List<String> imageKeys
) {

  public CatCommentImages toCommentImages() {
    return new CatCommentImages(imageKeys);
  }
}
