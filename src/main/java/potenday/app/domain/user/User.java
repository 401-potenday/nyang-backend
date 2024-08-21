package potenday.app.domain.user;

import static potenday.app.global.error.ErrorCode.*;

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
import org.hibernate.annotations.DynamicUpdate;
import potenday.app.domain.BaseTimeEntity;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@DynamicUpdate
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

  public void updateNickname(String nickname) {
    if (this.nickname != nickname) {
      this.nickname = nickname;
      setActive();
    }
  }

  public void markDelete() {
    if (isWithDraw) {
      throw new PotendayException(A001);
    }

    isWithDraw = true;
    deletedAt = LocalDateTime.now();
    activateStatus = UserActivateStatus.DELETED;
  }

  private void setActive() {
    this.activateStatus = UserActivateStatus.ACTIVATE;
  }

  public boolean isActive() {
    return activateStatus == UserActivateStatus.ACTIVATE;
  }

  public void authorizationCheck() {
    if (!isActive() || this.nickname == null) {
      throw new PotendayException(A005);
    }

    if (isWithDraw) {
      throw new PotendayException(A004);
    }
  }
}
