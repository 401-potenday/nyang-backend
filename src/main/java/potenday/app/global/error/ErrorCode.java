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
  C002("C002", "무리여부를 선택해주세요!. 잘못된 요청입니다. (YES, NO, UNSURE) 만 가능)", HttpStatus.BAD_REQUEST),
  C003("C003", "중성화여부를 선택해주세요! ", HttpStatus.BAD_REQUEST),
  C004("C004", "해당 컨텐츠는 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  // 컨텐츠 - 댓글
  C005("C005", "최대 3개의 이미지를 등록할 수 있습니다.", HttpStatus.BAD_REQUEST),
  C006("C006", "commentImageUris 필드는 필수입니다.", HttpStatus.BAD_REQUEST),
  C007("C007", "최대 3장의 사진까지 업로드 가능합니다.", HttpStatus.BAD_REQUEST),
  C008("C008", "최대 글자수(300)를 초과하였습니다", HttpStatus.BAD_REQUEST),

  // 컨텐츠 - 댓글 - 좋아요
  C009("C009", "이미 좋아요를 누르셨습니다.", HttpStatus.BAD_REQUEST),

  C010("C010",  "고양이 이름이 없어요. 고양이 이름은 필수입니다.", HttpStatus.BAD_REQUEST),
  C011("C011", "고양이 이름은 최소 2 글자, 최대 10 글자 사이어야 합니다.", HttpStatus.BAD_REQUEST),
  C012("C012", "이모지 번호는 필수입니다. 이모지 번호 1 ~ 18", HttpStatus.BAD_REQUEST),
  C016("C016", "글은 300자를 넘을 수 없습니다.", HttpStatus.BAD_REQUEST),

  C017("C017", "이미 삭제된 컨텐츠 입니다.", HttpStatus.BAD_REQUEST),

  // 컨텐츠 - 위도,경도
  CG01("CG01", "위도(lat)는 -90과 90 사이어야 합니다.", HttpStatus.BAD_REQUEST),
  CG02("CG02", "경도(lng)는 -180과 180 사이어야 합니다.", HttpStatus.BAD_REQUEST),
  CG03("CG03", "위도(lat)는 최소 소수점 5자리 이어야합니다.", HttpStatus.BAD_REQUEST),
  CG04("CG04", "경도(lng)는 최소 소수점 5자리 이어야합니다.", HttpStatus.BAD_REQUEST),
  CG05("CG05", "위도(lat)는 필수입니다.", HttpStatus.BAD_REQUEST),
  CG06("CG06", "경도(lng)는 필수입니다.", HttpStatus.BAD_REQUEST),

  // 컨텐츠 - 주소
  CA01("CA01", "주소 입력은 필수입니다.", HttpStatus.BAD_REQUEST),
  CA02("CA02", "위도 입력은 필수입니다.", HttpStatus.BAD_REQUEST),
  CA03("CA03", "경도 입력은 필수입니다.", HttpStatus.BAD_REQUEST),

  // 컨텐츠 - 이미지
  CI01("CI01", "잘못된 형식의 이미지 업로드입니다. 이미지의 uri 주소는 http:// 또는 https:// 로 시작해야 합니다.", HttpStatus.BAD_REQUEST),
  CI02("CI02", "최소 하나 이상의 고양이 사진이 필요합니다.", HttpStatus.BAD_REQUEST),
  CI03("CI03", "이미지 Key 는 UUID(v4) 포맷이어야 합니다.", HttpStatus.BAD_REQUEST),

  F001("F001", "이미 팔로우를 했어요", HttpStatus.BAD_REQUEST),
  F002("F002", "컨텐츠 번호 (contentId) 는 필수입니다.", HttpStatus.BAD_REQUEST),

  // 컨텐츠 - 신고
  R001("R001", "이미 처리 중인 신고입니다.", HttpStatus.CONFLICT),
  R002("R002", "신고 내용 입력은 필수입니다.", HttpStatus.BAD_REQUEST),
  R003("R003", "신고 내용 입력은 최대 300자 까지 입니다.", HttpStatus.BAD_REQUEST),
  R004("R004", "컨텐츠 번호(contentId)는 필수입니다.", HttpStatus.BAD_REQUEST),
  R005("R005", "신고 카테고리는 지정은 필수입니다.", HttpStatus.BAD_REQUEST),

  // 댓글
  D002("D001", "댓글 번호 (commentId) 는 필수입니다.", HttpStatus.BAD_REQUEST),
  D003("D002", "이미 삭제되었거나, 해당 댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  // 인증, 권한
  A001("A001", "잘못된 접근입니다. ", HttpStatus.UNAUTHORIZED),
  A002("A002", "리소스에 접근 권한이 없습니다. ", HttpStatus.UNAUTHORIZED),
  A003("A003", "유효하지 않은 토큰 값입니다. 다시 로그인 바랍니다.", HttpStatus.UNAUTHORIZED),
  A004("A004", "권한이 없습니다.", HttpStatus.UNAUTHORIZED),
  A005("A005", "닉네임이 없습니다. 닉네임을 설정하세요.", HttpStatus.UNAUTHORIZED),

  // 정의되지 않는 에러
  X001("X001", "서버에 문제가 발생하였습니다. 관리자에게 연락해주세요", HttpStatus.INTERNAL_SERVER_ERROR),
  X002("X002", "옳지 못한 형식의 요청입니다.", HttpStatus.BAD_REQUEST);

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
