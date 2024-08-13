package potenday.app.domain.cat.comment;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CatCommentImages {

  private final List<CatCommentImage> catCommentImages;

  public CatCommentImages(final List<String> commentImages) {
    this.catCommentImages = IntStream.range(0, commentImages.size())
        .mapToObj(order -> createContentImage(order, commentImages.get(order)))
        .collect(Collectors.toList());
  }

  private CatCommentImage createContentImage(int order, String imageKey) {
    return CatCommentImage.builder()
        .imageOrder(order)
        .imageKey(imageKey)
        .build();
  }

  public List<CatCommentImage> toTargetImages(long contentId, long commentId, long userId) {
    return catCommentImages.stream().map(image ->
            CatCommentImage.builder()
                .catContentId(contentId)
                .catCommentId(commentId)
                .userId(userId)
                .imageKey(image.getImageKey().toString())
                .imageOrder(image.getImageOrder())
                .build())
        .toList();
  }

}
