package potenday.app.query.model.comment;

import java.time.LocalDateTime;
import java.util.List;


public record CatCommentWithUserNicknameAndImages(
    long catCommentId,
    List<CatCommentImageWithOrder> catCommentImageWithOrders,
    String catCommentDesc,
    LocalDateTime catCommentCreatedAt,
    LocalDateTime catCommentUpdatedAt,
    String userNickname
) {

}
