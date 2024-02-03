package potenday.app.domain.cat.support;

import static potenday.app.global.cache.CacheConst.CAT_CONTENT_FOLLOW_COUNT;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import potenday.app.query.repository.CatContentQuery;

@Component
public class CatContentFollowingCountCalculator {

  private final CatContentQuery catContentQuery;

  public CatContentFollowingCountCalculator(CatContentQuery catContentQuery) {
    this.catContentQuery = catContentQuery;
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = CAT_CONTENT_FOLLOW_COUNT, key = "#catContentId")
  public long getFollowerCount(Long catContentId) {
    if (catContentId == null) {
      return 0;
    }
    return catContentQuery.computeFollowerCount(catContentId);
  }

}