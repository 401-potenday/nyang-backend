package potenday.app.domain.cat.comment;

import java.util.List;

public record AddCatCommentImages(List<String> commentImageUris) {

  public CatCommentImages toContentImages() {
    return new CatCommentImages(commentImageUris);
  }
}
