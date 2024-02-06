package potenday.app.domain.cat.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import potenday.app.global.error.ErrorCode;

class JibunAddressTest {

  @DisplayName("객체 생성시 완전한 full name 주소가 Null 또는 Empty 일 경우 예외 던진다 - 실패")
  @NullAndEmptySource
  @ParameterizedTest
  void builder(String address) {
    assertThatThrownBy(() -> JibunAddress.builder()
        .jibunAddrName(address)
        .build())
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasMessage(ErrorCode.CA01.getCode());
  }
}