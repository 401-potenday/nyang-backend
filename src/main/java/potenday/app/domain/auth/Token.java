package potenday.app.domain.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import potenday.app.domain.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TOKEN")
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
}
