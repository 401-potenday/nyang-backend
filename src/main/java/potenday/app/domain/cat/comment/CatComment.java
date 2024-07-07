package potenday.app.domain.cat.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import potenday.app.api.comment.UpdateCatComment;
import potenday.app.domain.BaseTimeEntity;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;

@Getter
@Entity
@Table(name = "CAT_CONTENT_COMMENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatComment extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "comment", columnDefinition = "VARCHAR(255)")
  private String comment;

  @Column(name = "cat_content_id", nullable = false)
  private Long catContentId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT")
  private boolean isDeleted;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  public CatComment(final String comment,final Long catContentId,final Long userId) {
    this.comment = comment;
    this.catContentId = catContentId;
    this.userId = userId;
  }

  public void setDeleted() {
    if (this.isDeleted) {
      throw new PotendayException(ErrorCode.D003);
    }
    this.isDeleted = true;
    this.deletedAt = LocalDateTime.now();
  }

  public void updateFrom(UpdateCatComment updateCatContent) {
    comment = updateCatContent.comment();
  }
}
