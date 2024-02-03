package potenday.app.api.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import potenday.app.query.model.comment.CatCommentWithUserNicknameAndImages;

/**
 *  "catCommentId": 1,
 *                 "catCommentImageUris: ["https://1.com"],
 *                 "catCommentDesc": "설명이 들어간다!",
 *                 "catCommentCreatedAt": "2022-10-10T21:21:11",
 *                 "catCommentUpdatedAt": "2022-10-10T21:21:11", <- 여기까지 캐시
 *
 *
 *                 "catCommentLikeCount": 3232,
 *                 "userNickname": "나는야집사" ,
 *                 "isCatCommentLiked: false
 */
public record CatCommentResponse(
    @JsonProperty("commentId") long commentId,
    @JsonProperty("commentDesc") String commentDesc,
    @JsonProperty("commentImageUris") List<String> commentImageUris,
    @JsonProperty("createdAt") String createdAt,
    @JsonProperty("updatedAt") String updatedAt,

    // 외부
    @JsonProperty("userNickname") String userNickname,
    @JsonProperty("commentLikeCount") long commentLikeCount,
    @JsonProperty("isCatCommentLiked") boolean isCatCommentLiked
) {

  public static CatCommentResponse of(
      CatCommentWithUserNicknameAndImages commentWithUserNicknameAndImages,
      long commentLikeCount,
      boolean isCatCommentLiked
  ) {
    return new CatCommentResponse(
        commentWithUserNicknameAndImages.catCommentId(),
        commentWithUserNicknameAndImages.catCommentDesc(),
        CatCommentImagesResponse.of(commentWithUserNicknameAndImages.catCommentImageWithOrders()),
        commentWithUserNicknameAndImages.catCommentCreatedAt().toString(),
        commentWithUserNicknameAndImages.catCommentUpdatedAt().toString(),
        commentWithUserNicknameAndImages.userNickname(),
        commentLikeCount,
        isCatCommentLiked
    );
  }

}
