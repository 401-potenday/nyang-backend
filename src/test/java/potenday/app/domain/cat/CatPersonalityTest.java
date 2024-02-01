package potenday.app.domain.cat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import potenday.app.domain.cat.status.CatPersonality;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.exception.ExternalException;

class CatPersonalityTest {

  @ParameterizedTest(name = "{index} => {0}")
  @EnumSource(CatPersonality.class)
  @DisplayName("Cat 성격을 찾습니다. - 성공")
  void findPersonalityCode(CatPersonality catPersonality) {
    assertThat(CatPersonality.from(catPersonality.name()).name())
        .isEqualTo(catPersonality.name());
  }

  @Test
  @DisplayName("존재하지 않는 Cat 성격의 경우 예외 던진다. - 실패")
  void throwExceptionNotFoundPersonality() {
    assertThatThrownBy(() -> CatPersonality.from("ANGRY_CAT"))
        .isExactlyInstanceOf(ExternalException.class)
        .hasMessage(ErrorCode.C001.getMessage());
  }
}