package potenday.app.global.config;

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
        "CAT_CONTENT_FOLLOW_COUNT",
        "CAT_CONTENT_COMMENT_LIKE_COUNT",
        "CAT_CONTENT_COMMENTS_LIST_WITH_PAGE"
    );
  }
}
