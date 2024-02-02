package potenday.app.domain.cat.follow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatFollowRepository extends JpaRepository<CatFollow, Long> {

  boolean existsByUserIdAndAndCatContentId(long userId, long contentId);

  boolean deleteByUserIdAndCatContentId(long userId, long contentId);
}
