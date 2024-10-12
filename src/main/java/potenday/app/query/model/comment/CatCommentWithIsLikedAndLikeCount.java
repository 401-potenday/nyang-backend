package potenday.app.query.model.comment;

import potenday.app.domain.cat.comment.CatComment;

public record CatCommentWithIsLikedAndLikeCount(
    CatComment catComment,
    long contentId,
    String catName,
    long commentLikedCount,
    boolean isCatCommentLiked
) {
}
