package potenday.app.query.model.comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import potenday.app.domain.cat.comment.CatComment;

public record CatCommentInfoDto(
    long catCommentId,
    String catCommentDesc,
    List<CatCommentImageWithOrder> catCommentImageWithOrders,
    LocalDateTime catCommentCreatedAt,
    LocalDateTime catCommentUpdatedAt
) {

  public static CatCommentInfoDto of(CatComment catComment) {
    return new CatCommentInfoDto(
        catComment.getId(),
        catComment.getComment(),
        catComment.getCommentImages()
            .stream()
            .map(CatCommentImageWithOrder::of)
            .collect(Collectors.toList()),
        catComment.getCreatedAt(),
        catComment.getUpdatedAt()
    );
  }
}
