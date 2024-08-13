package potenday.app.query.model.comment;

import potenday.app.domain.cat.comment.CatCommentImage;

public record CatCommentImageWithOrder(
    String commentImageUri,
    int order
) {

  public static CatCommentImageWithOrder of(CatCommentImage catCommentImage) {
    return new CatCommentImageWithOrder(catCommentImage.getImageUri(), catCommentImage.getImageOrder());
  }
}
