package potenday.app.event.listener;

import static potenday.app.global.cache.CacheConst.CAT_CONTENT_FOLLOW_COUNT;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import potenday.app.domain.cat.support.CatContentFollowingCountCalculator;
import potenday.app.event.action.FollowEvent;
import potenday.app.event.action.UnFollowEvent;

@Component
public class ContentFollowEventListener {

  private final CacheManager cacheManager;
  private final CatContentFollowingCountCalculator followingCountCalculator;

  public ContentFollowEventListener(CacheManager cacheManager,
      CatContentFollowingCountCalculator followingCountCalculator) {
    this.cacheManager = cacheManager;
    this.followingCountCalculator = followingCountCalculator;
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onFollow(FollowEvent followEvent) {
    Cache cache = cacheManager.getCache(CAT_CONTENT_FOLLOW_COUNT);
    Long currentCount = cache.get(followEvent.contentId(), Long.class);
    // cache miss
    if (currentCount == null) {
      followingCountCalculator.getFollowerCount(followEvent.contentId());
      return;
    }
    // cache update
    cache.put(followEvent.contentId(), currentCount + 1);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onUnfollow(UnFollowEvent unFollowEvent) {
    Cache cache = cacheManager.getCache(CAT_CONTENT_FOLLOW_COUNT);
    Long currentCount = cache.get(unFollowEvent.contentId(), Long.class);
    // cache miss
    if (currentCount == null) {
      followingCountCalculator.getFollowerCount(unFollowEvent.contentId());
      return;
    }
    // cache update
    cache.put(unFollowEvent.contentId(), currentCount - 1);
  }

}
