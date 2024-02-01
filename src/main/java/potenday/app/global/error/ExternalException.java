package potenday.app.global.error;

public class ExternalException extends IllegalArgumentException{

  private final ErrorCode errorCode;

  public ExternalException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  @Override
  public String getMessage() {
    return errorCode.getMessage();
  }
}
