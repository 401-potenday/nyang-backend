package potenday.app.api.report;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import potenday.app.api.common.ApiResponse;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.auth.AuthenticationPrincipal;
import potenday.app.domain.report.ReportService;

@RestController
public class ReportController {

  private final ReportService reportService;

  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  @PostMapping("/reports/content")
  public ApiResponse<?> doReport(
      @AuthenticationPrincipal AppUser appUser,
      @Valid @RequestBody CatContentReportRequest catContentReportRequest
  ) {
    reportService.report(appUser, catContentReportRequest);
    return ApiResponse.success("ok");
  }
}
