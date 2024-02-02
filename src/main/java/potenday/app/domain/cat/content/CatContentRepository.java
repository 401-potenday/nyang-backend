package potenday.app.domain.cat.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CatContentRepository extends JpaRepository<CatContent, Long> {


  @Query("select (count(c) > 0) from CatContent c where c.id = ?1 and c.isDeleted = false")
  boolean existsById(long contendId);
}
