package potenday.app.api.common;

public record ErrorContent(
    String code,
    String message
) {

  public static ErrorContent from(ErrorCode errorCode) {
    return new ErrorContent(errorCode.getCode(), errorCode.getMessage());
  }
}
