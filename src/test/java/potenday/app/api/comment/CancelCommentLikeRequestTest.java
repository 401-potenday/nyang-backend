package potenday.app.api.comment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import potenday.app.domain.cat.commentlikes.CancelCommentLike;

class CancelCommentLikeRequestTest {

  @Test
  void toCancelComment() {
    long commentId = 10;
    long contentId = 200;

    CancelCommentLikeRequest cancelCommentLikeRequest = new CancelCommentLikeRequest(commentId);
    CancelCommentLike cancelComment = cancelCommentLikeRequest.toCancelComment(contentId);

    assertThat(cancelComment.commentId()).isEqualTo(commentId);
    assertThat(cancelComment.contentId()).isEqualTo(contentId);
  }
}