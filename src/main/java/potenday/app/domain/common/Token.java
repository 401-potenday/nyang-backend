package potenday.app.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import potenday.app.domain.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "oauth_refresh_token", nullable = false, columnDefinition = "VARCHAR(255)")
  private String oAuthRefreshToken;

  @Column(name = "app_access_token", nullable = false, columnDefinition = "VARCHAR(255)")
  private String accessToken;

  @Column(name = "app_refresh_token", nullable = false, columnDefinition = "VARCHAR(255)")
  private String refreshToken;

  @Column(name = "oauth_user_uid", nullable = false)
  private String socialUid;

  @Column(name = "oauth_token_issued_at", nullable = false, columnDefinition = "NOW()")
  private LocalDateTime oauthRefreshIssuedAt;

  @Column(name = "app_refresh_issued_at", nullable = false, columnDefinition = "NOW()")
  private LocalDateTime appRefreshIssuedAt;
}
