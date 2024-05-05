package potenday.app.domain.report;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import potenday.app.api.report.CatContentReportRequest;
import potenday.app.domain.auth.AppUser;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;

@Service
public class ReportService {

  private final CatContentReportRepository catContentReportRepository;

  public ReportService(CatContentReportRepository catContentReportRepository) {
    this.catContentReportRepository = catContentReportRepository;
  }

  @Transactional
  public void report(AppUser appUser, CatContentReportRequest catContentReportRequest) {
    validateNotPendingReport(catContentReportRequest.getContentId());
    CatContentReportTarget reportTarget = catContentReportRequest.toTarget(appUser);
    catContentReportRepository.save(reportTarget.toReport());
  }

  private void validateNotPendingReport(long contentId) {
    boolean existed= catContentReportRepository.findPendingReportByContentId(contentId);
    if (existed) {
      throw new PotendayException(ErrorCode.R001);
    }
  }
}
