package potenday.app.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import potenday.app.global.property.RedisProperties;

@Configuration
class RedisConfig {

  @Bean
  public LettuceConnectionFactory redisConnectionFactory(
      RedisProperties redisProperties) {
    return new LettuceConnectionFactory(
        redisProperties.redisHost(),
        redisProperties.redisPort());
  }

  @Bean
  StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    StringRedisTemplate template = new StringRedisTemplate();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }
}