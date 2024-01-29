package potenday.app.global.error;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  E001("E001", "에러 메시지 내용이 들어갑니다.",  HttpStatus.BAD_REQUEST),

  // 유저 닉네임
  U001("U001", "이미 사용 중인 닉네임 입니다.", HttpStatus.CONFLICT),
  U002("U002", "닉네임은 3 ~ 15자 사이 이어야 합니다.", HttpStatus.BAD_REQUEST),
  U003("U003", "닉네임에는 비어있을 수 없습니다.", HttpStatus.BAD_REQUEST),
  U004("U004", "닉네임을 입력하세요", HttpStatus.BAD_REQUEST),
  U005("U005", "한글과 영문 문자만 허용됩니다.", HttpStatus.BAD_REQUEST),


  // 정의되지 않는 에러
  X001("X001", "서버에 문제가 발생하였습니다. 관리자에게 연락해주세요", HttpStatus.INTERNAL_SERVER_ERROR);

  private final String code;
  private final String message;
  private final HttpStatus httpStatus;

  private static final List<ErrorCode> errorCodes = Arrays.stream(ErrorCode.values()).toList();

  ErrorCode(String code, String message, HttpStatus httpStatus) {
    this.code = code;
    this.message = message;
    this.httpStatus = httpStatus;
  }

  public static ErrorCode findCode(String code) {
    return errorCodes.stream().filter(errorCode -> errorCode.getCode().equals(code))
        .findFirst()
        .orElse(X001);
  }
}
