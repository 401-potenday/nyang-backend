package potenday.app.api.comment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import potenday.app.domain.cat.commentlikes.AddCatCommentLike;

class AddCatCommentLikeRequestTest {

  @Test
  void toAddCatComment() {
    long commentId = 9;
    long contentId = 20;

    AddCatCommentLikeRequest addCatCommentLikeRequest = new AddCatCommentLikeRequest(commentId);
    AddCatCommentLike addCatComment = addCatCommentLikeRequest.toAddCatComment(contentId);

    assertThat(addCatComment.commentId()).isEqualTo(commentId);
    assertThat(addCatComment.contentId()).isEqualTo(contentId);
  }

  @Test
  void getCommentId() {
  }
}