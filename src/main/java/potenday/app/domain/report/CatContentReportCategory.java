package potenday.app.domain.report;

/**
 * IRRELEVANT_CONTENT: 게시글의 주제와 무관한 내용
 * INACCURATE_INFORMATION: 부정확한 정보가 포함되어 있음
 * INAPPROPRIATE_MEDIA: 부적절한 사진이 업로드되었음
 * OFFENSIVE_CONTENT: 욕설, 광고, 음란성, 도배성 등 부적절한 내용 포함
 * SUSPECTED_ANIMAL_ABUSE: 고양이 학대의 가능성이 있음
 */
public enum CatContentReportCategory {
  IRRELEVANT_CONTENT,
  INACCURATE_INFORMATION,
  INAPPROPRIATE_MEDIA,
  OFFENSIVE_CONTENT,
  SUSPECTED_ANIMAL_ABUSE,
  OTHER;

  public boolean requireContent() {
    return this == OTHER;
  }
}
