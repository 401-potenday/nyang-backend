package potenday.app.domain.cat.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potenday.app.domain.BaseTimeEntity;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;

@Getter
@Entity
@Table(name = "CAT_CONTENT_IMAGES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatContentImage extends BaseTimeEntity {

  private static final String IMAGE_HOST = "https://image.itthatcat.xyz";
  private static final Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "image_uri", nullable = false, columnDefinition = "VARCHAR(255)")
  private String imageUri;

  @Transient
  private UUID imageKey;

  @Column(name = "image_order", nullable = false, columnDefinition = "TINYINT")
  private int imageOrder;

  @Column(name = "cat_content_id", columnDefinition = "BIGINT")
  private Long catContentId;

  @Builder
  public CatContentImage(final String imageKey, final int imageOrder, final Long catContentId) {
    checkImageKeyFormat(imageKey);
    this.imageKey = UUID.fromString(imageKey);
    this.imageUri = generateImageUri(imageKey);
    this.imageOrder = imageOrder;
    if (catContentId != null) {
      setCatContentId(catContentId);
    }
  }

  public void setCatContentId(Long catContentId) {
    if (this.catContentId == null) {
      this.catContentId = catContentId;
    }
  }

  private void checkImageKeyFormat(String imageKey) {
    if (!UUID_REGEX.matcher(imageKey).matches()) {
      throw new PotendayException(ErrorCode.CI03);
    }
  }

  private String generateImageUri(String imageKey) {
    return IMAGE_HOST + "/" + imageKey + ".jpg";
  }

  public String extractKeyFromUri() {
    return this.imageUri.split(IMAGE_HOST + "/")[1].split(".jpg")[0];
  }
}
