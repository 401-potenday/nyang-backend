package potenday.app.api.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import potenday.app.domain.cat.comment.CatCommentImage;
import potenday.app.query.model.comment.CatCommentImageWithOrder;
import potenday.app.query.model.comment.CatCommentInfoDto;
import potenday.app.query.model.comment.CatCommentWithIsLikedAndAuthor;
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
    @JsonInclude(Include.NON_NULL)
    Long commentLikeCount,

    @JsonProperty("isCatCommentLiked")
    @JsonInclude(Include.NON_NULL)
    Boolean isCatCommentLiked,

    @JsonProperty("isAuthor")
    @JsonInclude(Include.NON_NULL)
    Boolean isAuthor,

    @JsonProperty("catName")
    @JsonInclude(Include.NON_NULL)
    String name
) {

  public static CatCommentResponse from(
      CatCommentWithIsLikedAndAuthor catCommentWithIsLikedAndAuthor
  ) {
    List<CatCommentImage> commentImages = catCommentWithIsLikedAndAuthor.catComment().getCommentImages();
    return new CatCommentResponse(
        catCommentWithIsLikedAndAuthor.catComment().getId(),
        catCommentWithIsLikedAndAuthor.catComment().getComment(),
        commentImages.stream()
            .map(CatCommentImage::getImageUri)
            .toList(),
        catCommentWithIsLikedAndAuthor.catComment().getCreatedAt().toString(),
        catCommentWithIsLikedAndAuthor.catComment().getUpdatedAt().toString(),
        null,
        catCommentWithIsLikedAndAuthor.userNickName(),
        catCommentWithIsLikedAndAuthor.commentLikedCount(),
        catCommentWithIsLikedAndAuthor.isCatCommentLiked(),
        catCommentWithIsLikedAndAuthor.isAuthor(),
        null
    );
  }

  public static CatCommentResponse of(
      CatCommentWithUserNicknameAndImages commentWithUserNicknameAndImages,
      long commentLikeCount,
      boolean isCatCommentLiked,
      boolean isAuthor
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
        isCatCommentLiked,
        isAuthor,
        null
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
        commentWithIsLikedAndLikeCount.isCatCommentLiked(),
        null,
        commentWithIsLikedAndLikeCount.catName()
    );
  }

  public static CatCommentResponse of(CatCommentInfoDto commentInfoDto) {
      return new CatCommentResponse(
          commentInfoDto.catCommentId(),
          commentInfoDto.catCommentDesc(),
          commentInfoDto.catCommentImageWithOrders()
              .stream()
              .map(CatCommentImageWithOrder::commentImageUri)
              .collect(Collectors.toList()),
          commentInfoDto.catCommentCreatedAt().toString(),
          commentInfoDto.catCommentUpdatedAt().toString(),
          null,
          null,
          null,
          null,
          null,
          null
      );
  }

}
