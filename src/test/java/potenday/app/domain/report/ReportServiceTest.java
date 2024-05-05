package potenday.app.domain.report;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import potenday.app.api.report.CatContentReportRequest;
import potenday.app.domain.auth.AppUser;

@MockitoSettings
class ReportServiceTest {

  @Mock
  CatContentReportRepository catContentReportRepository;

  @Test
  @DisplayName("컨텐츠 신고 시 컨텐츠(내용)가 없어도 에러를 던지지 않는다. - 성공")
  void report() {
    ReportService reportService = new ReportService(catContentReportRepository);
    CatContentReportRequest catContentReportRequest =
        new CatContentReportRequest(
            CatContentReportCategory.OFFENSIVE_CONTENT,
            null,
            1L
        );

    assertThatCode(() -> reportService.report(new AppUser(1L),
        catContentReportRequest)).doesNotThrowAnyException();

    verify(catContentReportRepository, times(1)).findPendingReportByContentId(anyLong());
    verify(catContentReportRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("카테고리가 OTHER 일때 content 가 null 이면 예외를 던진다. - 실패")
  void throwExceptionOtherCategoryAndNoContent() {
    ReportService reportService = new ReportService(catContentReportRepository);
    CatContentReportRequest catContentReportRequest =
        new CatContentReportRequest(
            CatContentReportCategory.OTHER,
            "fdsaf",
            1L
        );

    assertThatCode(() -> reportService.report(new AppUser(1L),
        catContentReportRequest)).doesNotThrowAnyException();

    verify(catContentReportRepository, times(1)).findPendingReportByContentId(anyLong());
    verify(catContentReportRepository, times(1)).save(any());
  }
}