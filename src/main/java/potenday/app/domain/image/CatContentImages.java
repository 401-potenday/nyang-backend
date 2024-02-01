package potenday.app.domain.image;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// CatContentImage 리스트를 만들고 관리함.
// 1. List<String> contentImages -> List<CatContentImage> contentImages
// 2. List<String> contentImages 순환하면 서 validation 오류 체크 (올바른 uri 형식인지)
// 3. List<String> contentImages 순환하면 서 순서대로 CatContentImage 생성
public class CatContentImages {

  private final List<CatContentImage> contentImages;

  public CatContentImages(final List<String> contentImages) {
    this.contentImages = IntStream.range(0, contentImages.size())
        .mapToObj(order -> createContentImage(order, contentImages.get(order)))
        .collect(Collectors.toList());
  }

  private CatContentImage createContentImage(int order, String uri) {
    return CatContentImage.builder()
        .imageOrder(order)
        .imageUri(uri)
        .build();
  }

  public List<CatContentImage> toTargetImages(Long contentId) {
    return contentImages.stream().map(image -> CatContentImage.builder()
            .catContentId(contentId)
            .imageUri(image.getImageUri())
            .imageOrder(image.getImageOrder())
            .build())
        .toList();
  }
}
