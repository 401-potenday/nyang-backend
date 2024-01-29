package potenday.app.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  E001("E001", "에러 메시지 내용이 들어갑니다.",  HttpStatus.BAD_REQUEST),

  // User
  U001("U001", "이미 사용 중인 닉네임 입니다.", HttpStatus.CONFLICT);

  private final String code;
  private final String message;
  private final HttpStatus httpStatus;

  ErrorCode(String code, String message, HttpStatus httpStatus) {
    this.code = code;
    this.message = message;
    this.httpStatus = httpStatus;
  }
}
