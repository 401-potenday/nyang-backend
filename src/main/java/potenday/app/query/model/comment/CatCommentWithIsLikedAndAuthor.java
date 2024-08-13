package potenday.app.query.model.comment;

import potenday.app.domain.cat.comment.CatComment;

public record CatCommentWithIsLikedAndAuthor(
    CatComment catComment,
    long contentId,
    String catName,
    long commentLikedCount,
    boolean isCatCommentLiked,
    String userNickName,
    boolean isAuthor
) {

}
