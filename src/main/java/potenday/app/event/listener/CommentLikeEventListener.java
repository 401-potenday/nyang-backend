package potenday.app.event.listener;

import static potenday.app.global.cache.CacheConst.CAT_COMMENT_USER_LIKED;
import static potenday.app.global.cache.CacheConst.CAT_CONTENT_COMMENT_LIKE_COUNT;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import potenday.app.domain.cat.support.CatCommentEngagementsCalculator;
import potenday.app.event.action.CommentLikeEvent;
import potenday.app.event.action.CommentUnlikeEvent;

@Slf4j
@Component
public class CommentLikeEventListener {

  private final CacheManager cacheManager;
  private final CatCommentEngagementsCalculator catCommentEngagementsCalculator;

  public CommentLikeEventListener(CacheManager cacheManager,
      CatCommentEngagementsCalculator catCommentEngagementsCalculator) {
    this.cacheManager = cacheManager;
    this.catCommentEngagementsCalculator = catCommentEngagementsCalculator;
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onCommentLike(CommentLikeEvent commentLikeEvent) {
    incrementCommentLikeCount(commentLikeEvent);

    Cache cache = cacheManager.getCache(CAT_COMMENT_USER_LIKED);
    String key = generateIsLikedKey(commentLikeEvent.catCommentLikeId().getUserId(), commentLikeEvent.catCommentLikeId().getCatCommentId());
    cache.put(key, true);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onCommentUnLike(CommentUnlikeEvent commentUnlikeEvent) {
    decreaseCommentLikeCount(commentUnlikeEvent);
    Cache cache = cacheManager.getCache(CAT_COMMENT_USER_LIKED);
    String key = generateIsLikedKey(commentUnlikeEvent.catCommentLikeId().getUserId(), commentUnlikeEvent.catCommentLikeId().getCatCommentId());
    cache.put(key, false);
  }

  private static String generateIsLikedKey(long userId, long catCommentId) {
    return userId + "_" + catCommentId;
  }

  private void decreaseCommentLikeCount(CommentUnlikeEvent commentUnlikeEvent) {
    Cache cache = cacheManager.getCache(CAT_CONTENT_COMMENT_LIKE_COUNT);
    Long currentCount = cache.get(commentUnlikeEvent.catCommentLikeId().getCatCommentId(), Long.class);
    if (currentCount == null) {
      catCommentEngagementsCalculator.getCommentLikesCount(
          commentUnlikeEvent.catCommentLikeId().getCatCommentId());
      return;
    }
    cache.put(commentUnlikeEvent.catCommentLikeId().getCatCommentId(), currentCount - 1);
  }


  private void incrementCommentLikeCount(CommentLikeEvent commentLikeEvent) {
    Cache cache = cacheManager.getCache(CAT_CONTENT_COMMENT_LIKE_COUNT);
    Long currentCount = cache.get(commentLikeEvent.catCommentLikeId().getCatCommentId(), Long.class);
    if (currentCount == null) {
      catCommentEngagementsCalculator.getCommentLikesCount(
          commentLikeEvent.catCommentLikeId().getCatCommentId());
      return;
    }
    cache.put(commentLikeEvent.catCommentLikeId().getCatCommentId(), currentCount + 1);
  }
}
