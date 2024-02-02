package potenday.app.domain.cat.commentlikes;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import potenday.app.domain.BaseTimeEntity;

@Entity
@Table(name = "CAT_CONTENT_COMMENT_LIKES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatCommentLike extends BaseTimeEntity {

  @EmbeddedId
  private CatCommentLikeId catCommentLikeId;

  public CatCommentLike(final CatCommentLikeId catCommentLikeId) {
    this.catCommentLikeId = catCommentLikeId;
  }
}
