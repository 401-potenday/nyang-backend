package potenday.app.api.content;

import java.util.List;
import potenday.app.domain.cat.content.CatContentImages;

public record UpdateCatContentImages(List<String> imageKeys) {

  public CatContentImages toContentImages() {
    return new CatContentImages(imageKeys);
  }
}
