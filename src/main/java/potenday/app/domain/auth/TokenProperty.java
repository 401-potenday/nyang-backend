package potenday.app.domain.auth;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TokenProperty {

    // TODO: 2024/01/29 설정파일에서 가져오도록 수정
    private String secret = "sample";
    private long tokenLifeTime = 600;
    private long tokenRefreshTime = 24*60*60; // 86400
}