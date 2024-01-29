package potenday.app.global.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    log.info("[error code] errCode = {}, message = {}, status = {}, instance = {}",
        ec.getCode(), ec.getMessage(), ec.getHttpStatus().value(), req.getRequestURI());
    return ResponseEntity.status(ec.getHttpStatus()).body(ApiResponse.error(ErrorContent.from(ec)));
  }

}
