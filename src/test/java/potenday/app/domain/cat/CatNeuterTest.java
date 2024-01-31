package potenday.app.domain.cat;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.exception.ExternalException;

class CatNeuterTest {

  @ParameterizedTest(name = "{index} => {0}")
  @EnumSource(CatNeuter.class)
  @DisplayName("중성화 유무 선택지를 찾습니다. - 성공")
  void findPersonalityCode(CatNeuter catNeuter) {
    assertThatCode(() -> CatNeuter.from(catNeuter.name()))
        .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("존재하지 않는 Cat 중성화 유무 선택지의 경우 예외 던진다. - 실패")
  void throwExceptionNotFoundPersonality() {
    assertThatThrownBy(() -> CatNeuter.from("KNOW"))
        .isExactlyInstanceOf(ExternalException.class)
        .hasMessage(ErrorCode.C002.getMessage());
  }
}