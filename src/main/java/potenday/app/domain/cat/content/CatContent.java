package potenday.app.domain.cat.content;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import potenday.app.domain.BaseTimeEntity;
import potenday.app.domain.cat.status.CatFriends;
import potenday.app.domain.cat.status.CatNeuter;
import potenday.app.domain.cat.status.CatPersonalities;
import potenday.app.domain.cat.vo.Coordinate;
import potenday.app.domain.cat.vo.JibunAddress;

@Getter
@Entity
@Table(name = "CAT_CONTENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
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

  @Column(name = "cat_personalities", nullable = false, columnDefinition = "VARCHAR(255)")
  @Convert(converter = CatPersonalitiesConverter.class)
  private CatPersonalities catPersonalities;

  @Column(name = "neuter", nullable = false, columnDefinition = "CHAR(10)")
  @Enumerated(value = EnumType.STRING)
  private CatNeuter neuter;

  @Embedded
  private JibunAddress jibunAddress;

  @Embedded
  private Coordinate coordinate;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "cat_emoji")
  private int catEmoji;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT")
  private boolean isDeleted;

  @Builder
  public CatContent(final Long id, final String name, final CatFriends hasFriends, final String description,
      final CatPersonalities catPersonalities, final CatNeuter neuter, final JibunAddress jibunAddress,
      final Coordinate coordinate, final int catEmoji) {
    this.id = id;
    this.name = name;
    this.hasFriends = hasFriends;
    this.description = description;
    this.catPersonalities = catPersonalities;
    this.neuter = neuter;
    this.jibunAddress = jibunAddress;
    this.coordinate = coordinate;
    this.catEmoji = catEmoji;
  }

  public void setOwner(final long userId) {
    if (this.userId != null && this.userId != userId) {
      return; // 이미 있는 사용자인 경우 처리하지 않음. 예외 처리
    }
    this.userId = userId;
  }
}