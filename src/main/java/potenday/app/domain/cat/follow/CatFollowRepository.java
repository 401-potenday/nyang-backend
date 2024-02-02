package potenday.app.domain.cat.follow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatFollowRepository extends JpaRepository<CatFollow, Long> {

  boolean existsCatFollowByUserIdAndCatContentId(long userId, long contentId);

  void deleteByUserIdAndCatContentId(long userId, long contentId);
}
