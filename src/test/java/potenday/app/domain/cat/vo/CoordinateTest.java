package potenday.app.domain.cat.vo;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CoordinateTest {

  @Test
  @DisplayName("Builder 를 이용하여 Coordinate 객체 생성 - 성공")
  void builder() {
    assertThatCode(() -> Coordinate.builder()
        .lat(38.123456)
        .lon(127.123456)
        .build())
        .doesNotThrowAnyException();
  }

  @ParameterizedTest
  @MethodSource("provideCoordinatesForTest")
  @DisplayName("소수점 5자리 미만 입력 시 예외 던짐 - 실패")
  void throwExceptionIfNotEnoughDecimalPlaces(double lat, double lon) {
    assertThatThrownBy(() -> Coordinate.builder()
        .lat(lat)
        .lon(lon)
        .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @ValueSource(doubles = {90.1, -90.1})
  @DisplayName("유효하지 않은 위도 범위 입력 시 예외 던짐 - 실패")
  void throwExceptionIfOutOfRangeLat(double lat) {
    assertThatThrownBy(() -> Coordinate.builder()
        .lat(lat)
        .lon(127.123456)
        .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @ValueSource(doubles = {180.1, -180.1})
  @DisplayName("유효하지 않은 경도 범위 입력 시 예외 던짐 - 실패")
  void throwExceptionIfOutOfRangeLon(double lon) {
    assertThatThrownBy(() -> Coordinate.builder()
        .lat(38.123456)
        .lon(lon)
        .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  private static Stream<Arguments> provideCoordinatesForTest() {
    return Stream.of(
        Arguments.of(38.1234, 127.1234),
        Arguments.of(38.123, 127.123),
        Arguments.of(38.12, 127.12),
        Arguments.of(38.1, 127.1),
        Arguments.of(38.0, 127.0)
    );
  }

}