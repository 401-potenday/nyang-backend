package potenday.app.global.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record RedisProperties(
    @Value("${spring.data.redis.port}") int redisPort,
    @Value("${spring.data.redis.host}") String redisHost
) {

}