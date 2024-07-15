package potenday.app.api.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Comparator;
import java.util.List;
import potenday.app.domain.cat.comment.CatCommentImage;
import potenday.app.query.model.comment.CatCommentWithIsLikedAndLikeCount;
import potenday.app.query.model.comment.CatCommentWithUserNicknameAndImages;

public record CatCommentResponse(
    @JsonProperty("commentId")
    long commentId,

    @JsonProperty("commentDesc")
    String commentDesc,

    @JsonProperty("commentImageUris")
    List<String> commentImageUris,

    @JsonProperty("createdAt")
    String createdAt,

    @JsonProperty("updatedAt")
    String updatedAt,

    @JsonProperty("contentId")
    @JsonInclude(Include.NON_DEFAULT)
    Long contentId,

    // 외부
    @JsonProperty("userNickname")
    @JsonInclude(Include.NON_NULL)
    String userNickname,

    @JsonProperty("commentLikeCount")
    long commentLikeCount,

    @JsonProperty("isCatCommentLiked")
    boolean isCatCommentLiked
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
        null,
        commentWithUserNicknameAndImages.userNickname(),
        commentLikeCount,
        isCatCommentLiked
    );
  }

  public static CatCommentResponse of(CatCommentWithIsLikedAndLikeCount commentWithIsLikedAndLikeCount) {
    List<CatCommentImage> commentImages = commentWithIsLikedAndLikeCount.catComment().getCommentImages();
    return new CatCommentResponse(
        commentWithIsLikedAndLikeCount.catComment().getId(),
        commentWithIsLikedAndLikeCount.catComment().getComment(),
        commentImages.stream()
            .map(CatCommentImage::getImageUri)
            .toList(),
        commentWithIsLikedAndLikeCount.catComment().getCreatedAt().toString(),
        commentWithIsLikedAndLikeCount.catComment().getUpdatedAt().toString(),
        commentWithIsLikedAndLikeCount.contentId(),
        null,
        commentWithIsLikedAndLikeCount.commentLikedCount(),
        commentWithIsLikedAndLikeCount.isCatCommentLiked()
    );
  }

}
