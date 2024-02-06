package potenday.app.domain.cat.comment;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import potenday.app.domain.user.User;

public class CatCommentImages {

  private final List<CatCommentImage> catCommentImages;

  public CatCommentImages(final List<String> commentImages) {
    this.catCommentImages = IntStream.range(0, commentImages.size())
        .mapToObj(order -> createContentImage(order, commentImages.get(order)))
        .collect(Collectors.toList());
  }

  private CatCommentImage createContentImage(int order, String uri) {
    return CatCommentImage.builder()
        .imageOrder(order)
        .imageUri(uri)
        .build();
  }

  public List<CatCommentImage> toTargetImages(long contentId, long commentId, User userId) {
    return catCommentImages.stream().map(image ->
            CatCommentImage.builder()
                .catContentId(contentId)
                .catCommentId(commentId)
                .userId(userId.getId())
                .imageUri(image.getImageUri())
                .imageOrder(image.getImageOrder())
                .build())
        .toList();
  }

}
