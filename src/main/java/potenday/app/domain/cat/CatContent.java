package potenday.app.domain.cat;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potenday.app.domain.BaseTimeEntity;
import potenday.app.domain.cat.status.CatFriends;
import potenday.app.domain.cat.status.CatNeuter;
import potenday.app.domain.cat.status.CatPersonality;
import potenday.app.domain.cat.vo.Coordinate;
import potenday.app.domain.cat.vo.JibunAddress;

@Getter
@Entity
@Table(name = "CAT_CONTENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatContent extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "has_friends", nullable = false, columnDefinition = "CHAR(10)")
  @Enumerated(value = EnumType.STRING)
  private CatFriends hasFriends;

  @Column(name = "description", nullable = false, columnDefinition = "VARCHAR(255)")
  private String description;

  @Column(name = "personality", nullable = false, columnDefinition = "CHAR(20)")
  @Enumerated(value = EnumType.STRING)
  private CatPersonality catPersonality;

  @Column(name = "neuter", nullable = false, columnDefinition = "CHAR(10)")
  @Enumerated(value = EnumType.STRING)
  private CatNeuter neuter;

  @Embedded
  private JibunAddress jibunAddress;

  @Embedded
  private Coordinate coordinate;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT")
  private boolean isDeleted;

  @Builder
  public CatContent(final Long id, final String name, final CatFriends hasFriends, final String description,
      final CatPersonality catPersonality, final CatNeuter neuter, final JibunAddress jibunAddress,
      final Coordinate coordinate) {
    this.id = id;
    this.name = name;
    this.hasFriends = hasFriends;
    this.description = description;
    this.catPersonality = catPersonality;
    this.neuter = neuter;
    this.jibunAddress = jibunAddress;
    this.coordinate = coordinate;
  }

  public void setOwner(final long userId) {
    if (this.userId != null && this.userId != userId) {
      return; // 이미 있는 사용자인 경우 처리하지 않음. 예외 처리
    }
    this.userId = userId;
  }
}