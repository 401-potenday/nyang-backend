package potenday.app.domain.cat.support;

import static potenday.app.global.cache.CacheConst.CAT_CONTENT_COMMENT_LIKE_COUNT;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import potenday.app.query.repository.CatCommentQuery;

@Component
public class CatCommentEngagementsCalculator {

  private final CatCommentQuery catCommentQuery;

  public CatCommentEngagementsCalculator(
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

  @Transactional(readOnly = true)
  // @Cacheable(cacheNames = CAT_CONTENT_COMMENTS_COUNT, key = "#catContentId")
  public long countCommentsByContentId(Long catContentId) {
    if (catContentId == null) {
      return 0;
    }
    return catCommentQuery.countContentComments(catContentId);
  }

  @Transactional(readOnly = true)
  // @Cacheable(cacheNames = CAT_COMMENT_USER_LIKED, key = "#userId + '_' + #catCommentId")
  public boolean isCommentLiked(Long userId,Long catCommentId) {
    if (userId == null || catCommentId == null) {
      return false;
    }
    return catCommentQuery.isCommentLiked(userId, catCommentId);
  }
}
