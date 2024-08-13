package potenday.app.domain.cat.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CatCommentImageTest {

  @Test
  @DisplayName("Builder 를 이용하여 CatCommentImage 객체 생성 - 성공")
  void builderTest() {
    assertThatCode(() -> CatCommentImage.builder()
        .userId(1L)
        .catCommentId(1L)
        .catContentId(1L)
        .imageOrder(1)
        .imageKey(UUID.randomUUID().toString())
        .build())
        .doesNotThrowAnyException();
  }

  @ParameterizedTest
  @ValueSource(strings = {"237beb84-eca4-4746-bd9e-19640dd8ccff.jpg", "237beb84-eca4-4746-bd9e-19640dd",})
  @DisplayName("Builder 로 객체 생성 시 ImageKey 가 UUID 포멧이 아니면 예외 던진다. - 실패")
  void checkUUIDImageKeyFormat(String value) {
    assertThatThrownBy(() -> CatCommentImage.builder()
        .userId(1L)
        .catCommentId(1L)
        .catContentId(1L)
        .imageOrder(1)
        .imageKey(value)
        .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("생성된 CatCommentImage 의 imageUri 는 Not Empty, 호스팅 이미지 URI - 성공" )
  void getImageUri() {
    String imageKey= UUID.randomUUID().toString();
    CatCommentImage catCommentImage = CatCommentImage.builder()
        .userId(1L)
        .catCommentId(1L)
        .catContentId(1L)
        .imageOrder(1)
        .imageKey(imageKey)
        .build();
    String imageUri = catCommentImage.getImageUri();

    // then
    assertThat(imageUri).isNotEmpty();
    assertThat(imageUri).isEqualTo("https://image.itthatcat.xyz/" + imageKey + ".jpg");
  }
}