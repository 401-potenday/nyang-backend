package potenday.app.domain.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record TokenProperty(
    @Value(("${jwt.secret:simple}")) String secret,
    @Value("${jwt.access-time-sec:1}") long accessTokenLifeTime,
    @Value("${jwt.refresh-time-sec:1}") long refreshTokenLifeTime
) {
}