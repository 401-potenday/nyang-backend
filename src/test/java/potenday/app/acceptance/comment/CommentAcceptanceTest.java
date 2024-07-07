package potenday.app.acceptance.comment;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.urlEncodingEnabled;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import potenday.app.acceptance.AcceptanceTest;
import potenday.app.domain.auth.TokenProvider;
import potenday.app.domain.cat.comment.CatComment;
import potenday.app.domain.cat.comment.CatCommentImage;
import potenday.app.domain.cat.comment.CatCommentImageRepository;
import potenday.app.domain.cat.comment.CatCommentImages;
import potenday.app.domain.cat.comment.CatCommentRepository;
import potenday.app.domain.cat.content.CatContent;
import potenday.app.domain.cat.content.CatContentImageRepository;
import potenday.app.domain.cat.content.CatContentRepository;
import potenday.app.domain.cat.status.CatFriends;
import potenday.app.domain.cat.status.CatNeuter;
import potenday.app.domain.cat.status.CatPersonalities;
import potenday.app.domain.cat.vo.Coordinate;
import potenday.app.domain.cat.vo.JibunAddress;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserOAuthProvider;
import potenday.app.domain.user.UserRepository;
import potenday.app.query.repository.CatCommentQuery;

@DisplayName("고양이 컨텐츠 댓글 인수 테스트")
public class CommentAcceptanceTest extends AcceptanceTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private CatContentRepository catContentRepository;

  @Autowired
  private CatCommentRepository catCommentRepository;

  @Autowired
  private EntityManager em;

  @Test
  @DisplayName("고양이 컨텐츠 댓글 삭제 - 성공")
  void deleteCatComment() {
    registerContent();
    given().log().all().contentType(ContentType.JSON)

        // when
        .when().header("Authorization", String.format("Bearer %s", userToken(1L)))
          .delete("/comments/{commentId}", 1L)

        // then
        .then()
          .log().all()
          .assertThat().statusCode(200)
          .assertThat().body("result", is("SUCCESS"));


    given().log().all().contentType(ContentType.JSON)

        // when
        .when().header("Authorization", String.format("Bearer %s", userToken(1L)))
          .delete("/comments/{commentId}", 1L)

        // then
        .then()
          .log().all()
          .assertThat().statusCode(404)
          .assertThat().body("result", is("ERROR"));

  }

  @Test
  @DisplayName("다른 유저가 컨텐츠 댓글 삭제 시 예외 발생 - 실패")
  void notOwnerDeleteComment() {
    registerContent();
    saveMember(2L);
    given().log().all().contentType(ContentType.JSON)

        // when
        .when().header("Authorization", String.format("Bearer %s", userToken(2L)))
          .delete("/comments/{commentId}", 1L)

        // then
        .then()
          .log().all()
          .assertThat().statusCode(404);
  }

  @Test
  @DisplayName("고양이 컨텐츠 댓글을 성공적으로 등록한다. - 성공")
  void registerContent() {
    saveMember(1L);
    saveContent(1L);

    given().log().all().contentType(ContentType.JSON)

        // when
        .when().header("Authorization", String.format("Bearer %s", userToken(1L))).body("""
            {
                "commentImageKeys": [
                  "0cb9b066-5d37-4788-8695-7fd929e06c21",
                  "0cb9b066-5d37-4788-8695-7fd929e06c23",
                  "0cb9b066-5d37-4788-8695-7fd929e06c23"
                  ],
                "commentDesc": "this is comment desc"
            }
            """).post("/contents/{contentId}/comments", 1L)

        // then
        .then()
          .log().all()
          .assertThat().statusCode(200)
          .assertThat().body("result", is("SUCCESS"))
          .assertThat().body("data.commentId", is(1));

  }

  @Test
  @DisplayName("고양이 컨텐츠 댓글을 성공적으로 수정한다. - 성공")
  void updateComments() {
    // given
    registerContent();
    final String newCommentDesc = "this is new commentDesc";

    given().log().all().contentType(ContentType.JSON)

        // when
        .when().header("Authorization", String.format("Bearer %s", userToken(1L))).body(String.format(
                """
                {
                    "commentImageKeys": [
                      "0cb9b066-5d37-4788-8695-7fd929e06c11",
                      "0cb9b066-5d37-4788-8695-7fd929e06c22",
                      "0cb9b066-5d37-4788-8695-7fd929e06c33"
                      ],
                    "commentDesc": "%s"
                }
                """, newCommentDesc)
            ).put("/comments/{commentId}", 1L)

        // then
        .then()
          .log().all()
          .assertThat().statusCode(200)
          .assertThat().body("result", is("SUCCESS"));

    CatComment catComment = catCommentRepository.findById(1L).get();


    TypedQuery<CatCommentImage> query1 = em
        .createQuery("SELECT cm FROM CatCommentImage cm WHERE cm.catContentId = :contentId", CatCommentImage.class)
        .setParameter("contentId", 1L);

    List<String> imgKeys = List.of(
        "https://image.itthatcat.xyz/0cb9b066-5d37-4788-8695-7fd929e06c11.jpg",
        "https://image.itthatcat.xyz/0cb9b066-5d37-4788-8695-7fd929e06c22.jpg",
        "https://image.itthatcat.xyz/0cb9b066-5d37-4788-8695-7fd929e06c33.jpg"
    );

    final List<String> updatedImageKeys = query1.getResultList()
        .stream().map(CatCommentImage::getImageUri)
        .toList();

    assertThat(catComment.getComment()).isEqualTo(newCommentDesc);
    assertThat(updatedImageKeys).containsAll(imgKeys);
  }

  private void saveMember(long userId) {
    userRepository.saveAndFlush(activeUser(userId));
  }

  private void saveContent(long userId) {
    catContentRepository.saveAndFlush(catContent(userId));
  }

  private CatContent catContent(long userId) {
    CatContent catContent = CatContent.builder()
        .id(1L)
        .catPersonalities(CatPersonalities.of(List.of("UNSURE")))
        .name("닝닝이")
        .catEmoji(10)
        .neuter(CatNeuter.NO)
        .hasFriends(CatFriends.NO)
        .coordinate(
            Coordinate.builder()
                .lat(37.11111)
                .lon(128.11111)
                .build()
        )
        .description("description")
        .jibunAddress(JibunAddress.builder()
            .jibunAddrName("jibun addr name")
            .build())
        .build();
    catContent.setOwner(userId);
    return catContent;
  }

  private String userToken(long id) {
    return tokenProvider.issueAccessToken(id);
  }

  private User activeUser(long userId) {
    User user = User.builder()
        .id(userId)
        .userOAuthProvider(UserOAuthProvider.KAKAO)
        .oAuthUid("oauth-uid")
        .build();
    user.updateNickname("nickname");
    return user;
  }
}
