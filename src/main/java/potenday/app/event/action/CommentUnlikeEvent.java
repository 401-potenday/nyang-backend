package potenday.app.event.action;

import potenday.app.domain.cat.commentlikes.CatCommentLikeId;

public record CommentUnlikeEvent(CatCommentLikeId catCommentLikeId) {

}
