package potenday.app.global.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import potenday.app.api.common.ApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PotendayException.class)
  public ResponseEntity<ApiResponse<ErrorContent>> handleInternalException(
      HttpServletRequest req,
      PotendayException e
  ) {
    ErrorCode ec = e.getErrorCode();
    if (ec.equals(ErrorCode.X001)) {
      log.error("[uncaught error] errCode = {}, message = {}, status = {}, instance = {}",
          ec.getCode(), ec.getMessage(), ec.getHttpStatus().value(), req.getRequestURI());
    } else {
      log.info("[potenday error] errCode = {}, message = {}, status = {}, instance = {}",
          ec.getCode(), ec.getMessage(), ec.getHttpStatus().value(), req.getRequestURI());
    }
    return ResponseEntity.status(ec.getHttpStatus()).body(ApiResponse.error(ErrorContent.from(ec)));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<ErrorContent>> handleMethodArgumentNotValidException(
      HttpServletRequest req,
      MethodArgumentNotValidException e
  ) {
    FieldError fieldError = e.getFieldErrors().get(0);
    ErrorCode ec = ErrorCode.findCode(fieldError.getDefaultMessage());

    // print log
    if (ec.getHttpStatus().is5xxServerError()) {
      log.error("[uncaught error], errCode = {}, message = {}, status = {}, instance = {}",
          ec.getCode(), ec.getMessage(), ec.getHttpStatus().value(), req.getRequestURI());
    } else {
      log.info("[external error], errCode = {}, message = {}, status = {}, instance = {}",
          ec.getCode(), ec.getMessage(), ec.getHttpStatus().value(), req.getRequestURI());
    }

    return ResponseEntity.status(ec.getHttpStatus())
        .body(ApiResponse.error(ErrorContent.from(ec)));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<ErrorContent>> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException e
  ) {
    log.warn(e.getMessage());
    return ResponseEntity.status(ErrorCode.X002.getHttpStatus())
        .body(ApiResponse.error(ErrorContent.from(ErrorCode.X002)));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<ErrorContent>> handleIllegalArgumentException(
      HttpServletRequest req,
      IllegalArgumentException e) {

    ErrorCode ec = ErrorCode.findCode(e.getMessage());
    if (ec == ErrorCode.X001) {
      log.error("Uncaught IllegalArgumentException occur,  message = {}", e.getMessage(),
          e.getCause().fillInStackTrace());
    } else {
      log.info("[external error], errCode = {}, message = {}, status = {}, instance = {}",
          ec.getCode(), ec.getMessage(), ec.getHttpStatus().value(), req.getRequestURI());
    }
    return ResponseEntity.status(ec.getHttpStatus())
        .body(ApiResponse.error(ErrorContent.from(ec)));
  }

  @ExceptionHandler(AuthenticationException.class)
   public ResponseEntity<ApiResponse<ErrorContent>> handleAuthenticationException(
      HttpServletRequest req,
      AuthenticationException e) {
    ErrorCode ec = e.getErrorCode();
    log.info("AuthenticationException, errCode = {}, message = {}, status = {}, instance = {}",
        ec.getCode(), ec.getMessage(), ec.getHttpStatus().value(), req.getRequestURI());
    return ResponseEntity.status(ec.getHttpStatus())
        .body(ApiResponse.error(ErrorContent.from(ec)));
  }
}
