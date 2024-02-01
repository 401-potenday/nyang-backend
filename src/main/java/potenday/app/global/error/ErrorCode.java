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
  U003("U003", "닉네임에는 공백이 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
  U004("U004", "닉네임을 입력하세요", HttpStatus.BAD_REQUEST),
  U005("U005", "한글과 영문 문자만 허용됩니다.", HttpStatus.BAD_REQUEST),
  U006("U006", "존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND),

  // 로그인
  L001("L001","지원하지 않는 OAuth Provider", HttpStatus.BAD_REQUEST),
  L002("L002", "내부 사용자 정보를 가져오는 데 문제가 발생하였습니다. ", HttpStatus.INTERNAL_SERVER_ERROR),
  L003("L003", "code 는 필수입니다.", HttpStatus.BAD_REQUEST),
  L004("L004", "redirectUri 는 필수입니다. ", HttpStatus.BAD_REQUEST),

  // 컨텐츠
  C001("C001", "존재하지 않는 고양이 성격입니다.", HttpStatus.BAD_REQUEST),
  C002("C002", "잘못된 요청입니다. (YES, NO, UNSURE) 만 가능)", HttpStatus.BAD_REQUEST),

  // 컨텐츠 - 위도,경도
  CG01("CG01", "위도(lat)는 -90과 90 사이어야 합니다.", HttpStatus.BAD_REQUEST),
  CG02("CG02", "경도(lon)는 -180과 180 사이어야 합니다.", HttpStatus.BAD_REQUEST),
  CG03("CG03", "위도(lat)는 최소 소수점 5자리 이어야합니다.", HttpStatus.BAD_REQUEST),
  CG04("CG04", "경도(lon)는 최소 소수점 5자리 이어야합니다.", HttpStatus.BAD_REQUEST),

  // 컨텐츠 - 주소
  CA01("CA01", "주소 입력은 필수입니다.", HttpStatus.BAD_REQUEST),

  // 컨텐츠 - 이미지
  CI01("CI01", "잘못된 형식의 이미지 업로드입니다. 이미지의 uri 주소는 http:// 또는 https:// 로 시작해야 합니다.", HttpStatus.BAD_REQUEST),

  // 인증, 권한
  A001("A001", "잘못된 접근입니다. ", HttpStatus.UNAUTHORIZED),

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
