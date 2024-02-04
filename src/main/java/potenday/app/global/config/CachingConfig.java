package potenday.app.global.config;

import static potenday.app.global.cache.CacheConst.CAT_COMMENT_USER_LIKED;
import static potenday.app.global.cache.CacheConst.CAT_CONTENT_COMMENTS_COUNT;
import static potenday.app.global.cache.CacheConst.CAT_CONTENT_COMMENTS;
import static potenday.app.global.cache.CacheConst.CAT_CONTENT_COMMENT_LIKE_COUNT;
import static potenday.app.global.cache.CacheConst.CAT_CONTENT_FOLLOW_COUNT;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {

  @Bean
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager(
        CAT_CONTENT_COMMENTS_COUNT,
        CAT_CONTENT_FOLLOW_COUNT,
        CAT_CONTENT_COMMENT_LIKE_COUNT,
        CAT_CONTENT_COMMENTS,
        CAT_COMMENT_USER_LIKED
    );
  }
}
