package potenday.app.event.listener;

import static potenday.app.global.cache.CacheConst.CAT_CONTENT_COMMENTS_COUNT;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import potenday.app.domain.cat.comment.AddCatComment;
import potenday.app.domain.cat.support.CatCommentEngagementsCalculator;

@Component
public class CommentEventListener {

  private final CacheManager cacheManager;
  private final CatCommentEngagementsCalculator catCommentEngagementsCalculator;

  public CommentEventListener(CacheManager cacheManager,
      CatCommentEngagementsCalculator catCommentEngagementsCalculator) {
    this.cacheManager = cacheManager;
    this.catCommentEngagementsCalculator = catCommentEngagementsCalculator;
  }

  // 댓글 추가
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onAddComment(AddCatComment addCatComment) {
    Cache cache = cacheManager.getCache(CAT_CONTENT_COMMENTS_COUNT);
    Long currentCount = cache.get(addCatComment.contentId(), Long.class);
    if (currentCount == null) {
      catCommentEngagementsCalculator.countCommentsByContentId(addCatComment.contentId());
      return;
    }
    cache.put(addCatComment.contentId(), currentCount + 1);
  }
}
