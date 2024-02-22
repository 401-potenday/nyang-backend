package potenday.app.domain.cat.content;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import potenday.app.global.error.PotendayException;

class CatContentImageTest {

  @Test
  @DisplayName("Builder 를 이용해서 CatContentImage 객체 생성하기 - 성공")
  void builderTest() {
    assertThatCode(() -> CatContentImage.builder()
        .imageKey(UUID.randomUUID().toString())
        .catContentId(1L)
        .imageOrder(1)
        .build())
        .doesNotThrowAnyException();
  }

  @ParameterizedTest
  @ValueSource(strings = {"237beb84-eca4-4746-bd9e-19640dd8ccff.jpg", "237beb84-eca4-4746-bd9e-19640dd",})
  @DisplayName("Builder 로 객체 생성 시 ImageKey 가 UUID 포멧이 아니면 예외 던진다. - 실패")
  void checkUUIDImageKeyFormat(String value) {
    assertThatThrownBy(() -> CatContentImage.builder()
        .imageKey(value)
        .catContentId(1L)
        .imageOrder(1)
        .build())
        .isInstanceOf(PotendayException.class);
  }

  @Test
  @DisplayName("contentId 가 이미 있는 경우 setCatContentId 는 무시 된다.  - 성공")
  void setCatContentId() {
    CatContentImage contentImage = CatContentImage.builder()
        .imageKey(UUID.randomUUID().toString())
        .catContentId(1L)
        .imageOrder(1)
        .build();

    contentImage.setCatContentId(2L);

    assertThat(contentImage.getCatContentId()).isEqualTo(1L);
  }
}