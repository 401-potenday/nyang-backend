package potenday.app.domain.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potenday.app.domain.BaseTimeEntity;

@Getter
@Entity
@Table(name = "CAT_CONTENT_IMAGES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatContentImage extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "image_uri", nullable = false, columnDefinition = "VARCHAR(255)")
  private String imageUri;

  @Column(name = "image_order", nullable = false, columnDefinition = "TINYINT")
  private int imageOrder;

  @Column(name = "cat_content_id", columnDefinition = "BIGINT")
  private Long catContentId;

  @Builder
  public CatContentImage(final String imageUri, final int imageOrder, Long catContentId) {
    validateImageHttpUri(imageUri);
    this.imageUri = imageUri;
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

  void validateImageHttpUri(String imageUri) {
    if (imageUri == null) {
      throw new IllegalArgumentException("imageUri is required");
    }
    if (!imageUri.startsWith("http://") && !imageUri.startsWith("https://")) {
      throw new IllegalArgumentException("imageUri must start with 'http://' or 'https://'");
    }
  }
}
