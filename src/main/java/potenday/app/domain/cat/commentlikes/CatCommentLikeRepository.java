package potenday.app.domain.cat.commentlikes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatCommentLikeRepository extends JpaRepository<CatCommentLike, Long> {

  boolean existsByCatCommentLikeId(CatCommentLikeId catCommentLikeId);
  void deleteByCatCommentLikeId(CatCommentLikeId catCommentLikeId);
}
