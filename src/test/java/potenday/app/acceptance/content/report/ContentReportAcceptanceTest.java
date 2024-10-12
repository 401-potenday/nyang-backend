package potenday.app.acceptance.content.report;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import potenday.app.acceptance.AcceptanceTest;
import potenday.app.domain.auth.TokenProvider;
import potenday.app.domain.cat.content.CatContent;
import potenday.app.domain.cat.content.CatContentRepository;
import potenday.app.domain.cat.status.CatFriends;
import potenday.app.domain.cat.status.CatNeuter;
import potenday.app.domain.cat.status.CatPersonalities;
import potenday.app.domain.cat.vo.Coordinate;
import potenday.app.domain.cat.vo.JibunAddress;
import potenday.app.domain.report.CatContentReport;
import potenday.app.domain.report.CatContentReportCategory;
import potenday.app.domain.report.CatContentReportRepository;

@DisplayName("고양이 컨텐츠 신고 인수 테스트")
public class ContentReportAcceptanceTest extends AcceptanceTest {

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private CatContentReportRepository catContentReportRepository;

  @Autowired
  private CatContentRepository catContentRepository;

  @Test
  @DisplayName("고양이 컨텐츠 신고 200 - 성공")
  void reportContent() {
    saveContent(1L);

    given().contentType(ContentType.JSON)

        // when
        .when().header("Authorization", String.format("Bearer %s", userToken(1L))).body("""
            {
                "category": "IRRELEVANT_CONTENT",
                "content": "신고!!",
                "contentId": 1
            }
            """).post("/reports/content")

        // then
        .then()
          .assertThat().body("result", is("SUCCESS"))
          .assertThat().statusCode(200);
  }

  @Test
  @DisplayName("게시글 중복 신고시 409 - 실패")
  void throwExceptionWhenDoublyReportContent() {
    saveReport(1L);

    given().contentType(ContentType.JSON)

        // when
        .when().header("Authorization", String.format("Bearer %s", userToken(1L))).body("""
            {
                "category": "IRRELEVANT_CONTENT",
                "content": "신고!!",
                "contentId": 1
            }
            """).post("/reports/content")

        // then
        .then()
          .assertThat().body("result", is("ERROR"))
          .assertThat().body("error.code", is("R001"))
          .assertThat().body("error.message", is("이미 처리 중인 신고입니다."))
          .assertThat().statusCode(409);

  }

  @Test
  @DisplayName("신고된 게시글 조회시 예외 발생 403 - 실패")
  void throwExceptionWhenRetrieveReportedContent() {
    saveReport(1L);

    given().contentType(ContentType.JSON)

        // when
        .when().get("/contents/{id}", 1L)

        // then
        .then()
          .assertThat().body("result", is("SUCCESS"))
          .assertThat().body("data.contentId", is(0))
          .assertThat().statusCode(200);
  }

  private void saveReport(long contentId) {
    catContentReportRepository.save(catContentReport(contentId));
  }

  private void saveContent(long contentId) {
    catContentRepository.save(catContent(contentId));
  }

  private CatContent catContent(long contentId) {
    return CatContent.builder()
        .id(contentId)
        .catPersonalities(CatPersonalities.of(List.of("LOVES_TO_CUDDLE")))
        .name("냐옹이")
        .catEmoji(1)
        .jibunAddress(JibunAddress.builder()
            .jibunAddrName("출몰주소")
            .build())
        .hasFriends(CatFriends.NO)
        .coordinate(Coordinate.builder()
            .lon(127.23132)
            .lat(37.23123)
            .build())
        .description("설명")
        .neuter(CatNeuter.NO)
        .build();
  }

  private CatContentReport catContentReport(long contentId) {
    return CatContentReport.builder()
        .category(CatContentReportCategory.OFFENSIVE_CONTENT)
        .contentId(contentId)
        .content("hhh")
        .reporterId(1L)
        .build();
  }

  private String userToken(long id) {
    return tokenProvider.issueAccessToken(id);
  }


}
