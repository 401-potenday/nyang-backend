package potenday.app.domain.cat.content;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CatContentImageRepository extends JpaRepository<CatContentImage, Long> {

  List<CatContentImage> findByCatContentId(long contentId);

  @Transactional
  @Modifying
  @Query("delete from CatContentImage c where c.catContentId = ?1")
  void deleteAllByCatContentId(long contentId);
}
