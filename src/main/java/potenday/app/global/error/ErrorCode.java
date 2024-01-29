package potenday.app.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
  E001("E001", "에러 메시지 내용이 들어갑니다.");

  private final String code;
  private final String message;

  ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }
}
