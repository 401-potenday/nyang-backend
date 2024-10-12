package potenday.app.domain.cat.comment;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CatCommentRepository extends JpaRepository<CatComment, Long> {

  @Query(value = "select c from CatComment c where c.userId = :userId and c.id = :commentId")
  Optional<CatComment> findUserComment(long userId, long commentId);
}
