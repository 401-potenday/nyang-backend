package potenday.app.domain.report;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import potenday.app.domain.BaseTimeEntity;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;

@Getter
@Entity
@Table(name = "CAR_CONTENT_REPORTS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatContentReport extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "category", columnDefinition = "CHAR(25)")
  @Enumerated(value = EnumType.STRING)
  private CatContentReportCategory category;

  @Column(name = "report_memo")
  @Length(max = 300)
  private String content;

  @Column(name = "report_content_id", columnDefinition = "BIGINT")
  private long contentId;

  @Column(name = "repoter_id")
  private long reporterId;

  @Column(name = "status", columnDefinition = "CHAR(10)")
  @Enumerated(value = EnumType.STRING)
  private CatContentReportStatus status;

  @Builder
  public CatContentReport(
      final CatContentReportCategory category,
      final String content,
      final long contentId,
      final long reporterId) {
    assert category != null;
    assert contentId != 0L;
    assert reporterId != 0L;

    // 만약 category 가 직접입력하기(OTHER) 이면 무.조.건 content 가 있어야 한다.
    // category 가 ETC 가 아니면 content 는 무시된다.
    if (category.requireContent()) {
      checkContentNotNullAndBlank(content);
      this.content = content;
    }

    this.category = category;
    this.contentId = contentId;
    this.reporterId = reporterId;
    this.status = CatContentReportStatus.PENDING;
  }

  private void checkContentNotNullAndBlank(String content) {
    if (content == null || content.isBlank()) {
      throw new PotendayException(ErrorCode.R002);
    }
  }

}
