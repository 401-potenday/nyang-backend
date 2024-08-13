package potenday.app.domain.report;

import potenday.app.domain.auth.AppUser;

public record CatContentReportTarget(
    CatContentReportCategory category,
    String content,
    long contentId,
    AppUser appUser
) {

  public CatContentReport toReport() {
    return CatContentReport.builder()
        .reporterId(appUser.id())
        .category(category)
        .content(content)
        .contentId(contentId)
        .category(category)
        .build();
  }
}
