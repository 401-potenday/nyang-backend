package potenday.app.domain.cat.follow;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potenday.app.domain.BaseTimeEntity;

@Getter
@Entity
@Table(name = "CAT_CONTENT_FOLLOW")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatFollow extends BaseTimeEntity {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private long userId;

  @Column(name = "cat_content_id", nullable = false)
  private long catContentId;

  public CatFollow(long userId, long catContentId) {
    this.userId = userId;
    this.catContentId = catContentId;
  }
}
