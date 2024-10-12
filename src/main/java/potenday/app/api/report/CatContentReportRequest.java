package potenday.app.api.report;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.report.CatContentReportCategory;
import potenday.app.domain.report.CatContentReportTarget;

@Getter
public class CatContentReportRequest {

  @NotNull(message = "R005")
  private CatContentReportCategory category;

  @Length(max = 300, message = "R003")
  private String content;

  @NotNull(message = "R004")
  private Long contentId;

  public CatContentReportRequest(
      CatContentReportCategory category,
      String content,
      long contentId) {
    this.category = category;
    this.content = content;
    this.contentId = contentId;
  }

  public CatContentReportTarget toTarget(AppUser appUser) {
    return new CatContentReportTarget(category, content, contentId, appUser);
  }
}
