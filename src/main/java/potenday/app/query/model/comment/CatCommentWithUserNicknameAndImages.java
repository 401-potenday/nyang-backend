package potenday.app.query.model.comment;

import java.time.LocalDateTime;
import java.util.List;

/**
 *         "catCommentId": 1,
 *         "catCommentImageUris: ["https://1.com"],
 *         "catCommentDesc": "설명이 들어간다!",
 *         "catCommentCreatedAt": "2022-10-10T21:21:11",
 *         "catCommentUpdatedAt": "2022-10-10T21:21:11",
 *         "userNickname": "유저닉네임", <- 여기까지 캐시
 *
 *
 *         "catCommentLikeCount": 3232,
 *         "isCatCommentLiked: false
 */
public record CatCommentWithUserNicknameAndImages(
    long catCommentId,
    List<CatCommentImageWithOrder> catCommentImageWithOrders,
    String catCommentDesc,
    LocalDateTime catCommentCreatedAt,
    LocalDateTime catCommentUpdatedAt,
    String userNickname
) {

}
