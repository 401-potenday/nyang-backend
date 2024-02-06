package potenday.app.api.comment;

import java.util.Comparator;
import java.util.List;
import potenday.app.query.model.comment.CatCommentImageWithOrder;

public record CatCommentImagesResponse(
    List<String> commentImageUris
) {

  public static List<String> of(List<CatCommentImageWithOrder> catCommentImageWithOrder) {
    return catCommentImageWithOrder.stream()
        .sorted(Comparator.comparingInt(CatCommentImageWithOrder::order))
        .map(CatCommentImageWithOrder::commentImageUri)
        .toList();
  }

}
