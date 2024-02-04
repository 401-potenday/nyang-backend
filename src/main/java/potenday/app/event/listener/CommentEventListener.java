package potenday.app.event.listener;

import static potenday.app.global.cache.CacheConst.CAT_CONTENT_COMMENTS_COUNT;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import potenday.app.domain.cat.comment.AddCatComment;

@Component
public class CommentEventListener {

  private final CacheManager cacheManager;

  public CommentEventListener(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onAddComment(AddCatComment addCatComment) {
    Cache cache = cacheManager.getCache(CAT_CONTENT_COMMENTS_COUNT);
    Long currentCount = cache.get(addCatComment.contentId(), Long.class);
    if (currentCount == null) {
      return;
    }
    cache.put(addCatComment.contentId(), currentCount + 1);
  }
}
