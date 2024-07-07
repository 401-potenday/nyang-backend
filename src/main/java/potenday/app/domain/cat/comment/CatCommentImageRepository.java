package potenday.app.domain.cat.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CatCommentImageRepository extends JpaRepository<CatCommentImage, Long> {

  @Transactional
  @Modifying
  @Query("delete from CatCommentImage c where c.catCommentId = :commentId")
  void deleteAllByCommentId(long commentId);
}
