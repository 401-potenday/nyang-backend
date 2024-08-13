package potenday.app.domain.report;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

  @Transactional(readOnly = true)
  public void checkReportContent(long contentId) {
    boolean isReported = isReportByContentId(contentId);
    if (isReported) {
      throw new PotendayException(ErrorCode.R006);
    }
  }

  private void validateNotPendingReport(long contentId) {
    boolean existed= isReportByContentId(contentId);
    if (existed) {
      throw new PotendayException(ErrorCode.R001);
    }
  }

  @Transactional(readOnly = true)
  public boolean isReportByContentId(long contentId) {
    return catContentReportRepository.findPendingReportByContentId(contentId);
  }
}
