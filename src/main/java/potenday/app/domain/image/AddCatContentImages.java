package potenday.app.domain.image;

import java.util.List;

public record AddCatContentImages(List<String> contentImages) {

  public CatContentImages toContentImages() {
    return new CatContentImages(contentImages);
  }
}
