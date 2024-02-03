package potenday.app.event.listener;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import potenday.app.event.action.UnFollowEvent;
import potenday.app.event.action.FollowEvent;

@Component
public class ContentFollowEventListener {

  private final CacheManager cacheManager;

  public ContentFollowEventListener(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onFollow(FollowEvent event) {

  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onUnfollow(UnFollowEvent event) {

  }

}
