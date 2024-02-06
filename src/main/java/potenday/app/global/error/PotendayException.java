package potenday.app.global.error;

public class PotendayException extends RuntimeException{

  private final ErrorCode errorCode;

  public PotendayException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
