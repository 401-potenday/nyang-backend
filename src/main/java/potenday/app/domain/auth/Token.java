package potenday.app.domain.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import potenday.app.domain.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "TOKEN")
public class Token extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "app_refresh_token", nullable = false, columnDefinition = "VARCHAR(255)")
  private String appRefreshToken;

  @Column(name = "oauth_refresh_token", nullable = false, columnDefinition = "VARCHAR(255)")
  private String oauthRefreshToken;

  @Column(name = "oauth_user_uid", nullable = false)
  private String socialUid;

  public Token(String socialUid, String oauthRefreshToken, String appRefreshToken) {
    this.socialUid = socialUid;
    this.oauthRefreshToken = oauthRefreshToken;
    this.appRefreshToken = appRefreshToken;
  }

  public void updateRt(String appRefreshToken) {
    this.appRefreshToken = appRefreshToken;
  }
}
