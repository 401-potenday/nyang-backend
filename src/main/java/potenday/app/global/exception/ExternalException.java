package potenday.app.global.exception;

import potenday.app.global.error.ErrorCode;

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
