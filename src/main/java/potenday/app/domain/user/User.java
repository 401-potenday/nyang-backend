package potenday.app.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import potenday.app.domain.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 15)
  @Column(name = "nickname", columnDefinition = "VARCHAR(15)")
  private String nickname;

  @Column(name = "oauth_user_uid", nullable = false)
  private String oAuthUid;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "provider", nullable = false, columnDefinition = "CHAR(10)")
  private UserOAuthProvider userOAuthProvider;

  @Column(name = "is_withdraw", columnDefinition = "TINYINT")
  private boolean isWithDraw = false;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "pic_url", columnDefinition = "VARCHAR(255)")
  private String picUrl;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "activate_status", columnDefinition = "CHAR(10)")
  private UserActivateStatus activateStatus;

  @Builder
  public User(final Long id, final String oAuthUid,
      final UserOAuthProvider userOAuthProvider) {
    this.id = id;
    this.oAuthUid = oAuthUid;
    this.userOAuthProvider = userOAuthProvider;
    this.isWithDraw = false;
    this.activateStatus = UserActivateStatus.PENDING;
  }

  public String getNickname() {
    return nickname;
  }

  public Long getId() {
    return id;
  }

  public String getoAuthUid() {
    return oAuthUid;
  }
}
