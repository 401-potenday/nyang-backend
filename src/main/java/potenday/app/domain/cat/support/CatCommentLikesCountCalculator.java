package potenday.app.domain.cat.support;

import static potenday.app.global.cache.CacheConst.CAT_CONTENT_COMMENT_LIKE_COUNT;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import potenday.app.query.repository.CatCommentQuery;

@Component
public class CatCommentLikesCountCalculator {

  private final CatCommentQuery catCommentQuery;

  public CatCommentLikesCountCalculator(
      CatCommentQuery catCommentQuery) {
    this.catCommentQuery = catCommentQuery;
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = CAT_CONTENT_COMMENT_LIKE_COUNT, key = "#catCommentId")
  public long getCommentLikesCount(Long catCommentId) {
    if (catCommentId == null) {
      return 0;
    }
    return catCommentQuery.computeCommentLikesCount(catCommentId);
  }
}
