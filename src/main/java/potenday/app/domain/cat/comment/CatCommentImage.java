package potenday.app.domain.cat.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potenday.app.domain.BaseTimeEntity;

@Getter
@Entity
@Table(name = "CAT_CONTENT_COMMENT_IMAGES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatCommentImage extends BaseTimeEntity {

  private static final String IMAGE_HOST = "https://image.itthatcat.xyz";
  private static final Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "cat_content_id", nullable = false)
  private Long catContentId;

  @Column(name = "cat_comment_id", nullable = false)
  private Long catCommentId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "image_uri", nullable = false, columnDefinition = "VARCHAR(255)")
  private String imageUri;

  @Transient
  private UUID imageKey;

  @Column(name = "image_order", nullable = false, columnDefinition = "TINYINT")
  private int imageOrder;

  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Builder
  public CatCommentImage(
      final long catContentId,
      final long catCommentId,
      final long userId,
      final String imageKey,
      final int imageOrder
  ) {
    checkImageKeyFormat(imageKey);
    this.catContentId = catContentId;
    this.catCommentId = catCommentId;
    this.userId = userId;
    this.imageKey = UUID.fromString(imageKey);
    this.imageUri = generateImageUri(imageKey);
    this.imageOrder = imageOrder;
  }

  private void checkImageKeyFormat(String imageKey) {
    if (!UUID_REGEX.matcher(imageKey).matches()) {
      throw new IllegalArgumentException("CI03");
    }
  }

  private String generateImageUri(String imageKey) {
    return IMAGE_HOST + "/" + imageKey + ".jpg";
  }
}
