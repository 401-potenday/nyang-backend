package potenday.app.domain.cat.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CatCommentImagesTest {

  private static final String key1 = UUID.randomUUID().toString();
  private static final String key2 = UUID.randomUUID().toString();

  @Test
  @DisplayName("CatCommentImages 생성 시 올바른 객체 생성 검증 - 성공")
  void catCommentImagesInitializationTest() {
    // when
    List<String> commentImages = Arrays.asList(key1, key2);
    CatCommentImages catCommentImages = new CatCommentImages(commentImages);

    // then
    assertThat(catCommentImages.toTargetImages(1, 1, 1)).hasSize(commentImages.size())
        .extracting("imageKey", "imageOrder")
        .containsOnly(
            tuple(UUID.fromString(key1), 0),
            tuple( UUID.fromString(key2), 1)
        );
  }

  @Test
  @DisplayName("CatCommentImages 변환 시 올바른 URI와 순서 부여 검증 - 성공")
  void toTargetImagesTest() {
    // given
    List<String> commentImages = Arrays.asList(key1, key2);
    long contentId = 1L;
    long commentId = 2L;
    long userId = 3L;

    // when
    CatCommentImages catCommentImages = new CatCommentImages(commentImages);
    List<CatCommentImage> targetImages = catCommentImages.toTargetImages(contentId, commentId, userId);

    // then
    assertThat(targetImages)
        .hasSize(commentImages.size())
        .allMatch(image -> image.getCatContentId() == contentId)
        .allMatch(image -> image.getCatCommentId() == commentId)
        .allMatch(image -> image.getUserId() == userId)
        .extracting("imageUri", "imageOrder")
        .containsOnly(
            // 주어진 imageKey 리스트 순서와 동일하게 order 가 부여되어야한다.
            tuple("https://image.itthatcat.xyz/" + key1 + ".jpg", 0),
            tuple("https://image.itthatcat.xyz/" + key2 + ".jpg", 1)
        );
  }
}