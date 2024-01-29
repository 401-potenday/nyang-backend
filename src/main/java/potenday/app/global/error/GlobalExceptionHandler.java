package potenday.app.global.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    log.info("[potenday error] errCode = {}, message = {}, status = {}, instance = {}",
        ec.getCode(), ec.getMessage(), ec.getHttpStatus().value(), req.getRequestURI());
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

}
