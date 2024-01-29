package potenday.app.api;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import potenday.app.api.common.ApiResponse;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.ErrorContent;
import potenday.app.api.common.PageContent;

@RestController
public class ExampleController {

  // 성공 예시[단 건]
  @GetMapping("/example/success")
  public ApiResponse<?> exampleSuccessSingleData() {
    return ApiResponse.success(new Example(1));
  }

  // 성공 예시[여러 건]
  @GetMapping("/example/success/multiply")
  public ApiResponse<?> exampleSuccessMultipleData() {
    PageContent<Example> pageContent = new PageContent<>(
        List.of(new Example(1), new Example(2)), 1, 2, 10, 20);
    return ApiResponse.success(pageContent);
  }

  // 실패 예시
  @GetMapping("/example/fail")
  public ApiResponse<?> exampleFail() {
    return ApiResponse.error(ErrorContent.from(ErrorCode.E001));
  }

  record Example(long id) {

  }

}
