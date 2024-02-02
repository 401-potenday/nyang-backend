package potenday.app.domain.cat.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import potenday.app.domain.BaseTimeEntity;

@Getter
@Entity
@Table(name = "CAT_CONTENT_COMMENT_IMAGES")
@SQLRestriction("is_deleted <> false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatCommentImage extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "cat_content_id", nullable = false)
  private Long catContentId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "image_uri", nullable = false, columnDefinition = "VARCHAR(255)")
  private String imageUri;

  @Column(name = "image_order", nullable = false, columnDefinition = "TINYINT")
  private int imageOrder;

  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Builder
  public CatCommentImage(Long catContentId, Long userId, String imageUri, int imageOrder) {
    validateImageHttpUri(imageUri);
    this.catContentId = catContentId;
    this.userId = userId;
    this.imageUri = imageUri;
    this.imageOrder = imageOrder;
  }

  void validateImageHttpUri(String imageUri) {
    if (!imageUri.startsWith("http://") && !imageUri.startsWith("https://")) {
      throw new IllegalArgumentException("CI01");
    }
  }
}
