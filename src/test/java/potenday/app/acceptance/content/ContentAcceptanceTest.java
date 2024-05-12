package potenday.app.acceptance.content;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import potenday.app.acceptance.AcceptanceTest;
import potenday.app.domain.auth.TokenProvider;
import potenday.app.domain.cat.content.CatContentImage;
import potenday.app.domain.cat.content.CatContentImageRepository;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserOAuthProvider;
import potenday.app.domain.user.UserRepository;

@DisplayName("고양이 컨텐츠 인수 테스트")
public class ContentAcceptanceTest extends AcceptanceTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private CatContentImageRepository catContentImageRepository;

  @Test
  @DisplayName("고양이 컨텐츠를 성공적으로 등록한다. - 성공")
  void registerCourse() {
    // given
    saveMember();
    given().contentType(ContentType.JSON)

        // when
        .when().header("Authorization", String.format("Bearer %s", userToken(1L))).body("""
            {
                "lng": "126.223123",
                "lat": "37.212321",
                "imageKeys": [
                  "0cb9b066-5d37-4788-8695-7fd929e06c21",
                  "0cb9b066-5d37-4788-8695-7fd929e06c22",
                  "0cb9b066-5d37-4788-8695-7fd929e06c23"
                ],
                "jibunAddrName": "서울 마포구 망원동 415-31",
                "jibunMainAddrNo": "main",
                "jibunSido": "시도",
                "jibunSigungu": "시군구",
                "jibunDong": "지번동",
                "jibunSubAddrNo": "서브동",
                "neuter": "YES",
                "group": "YES",
                "catPersonalities": ["UNSURE"],
                "description": "ds",
                "name": "냥이이름",
                "catEmoji": 10
            }
            """).post("/contents")

        // then
        .then()
          .assertThat().body("result", is("SUCCESS"))
          .assertThat().body("data.contentId", is(1))
          .assertThat().statusCode(200);

    List<CatContentImage> catContentImages = catContentImageRepository.findAll();
    assertThat(catContentImages).hasSize(3);
    assertThat(catContentImages)
        .allMatch(catContentImage -> catContentImage.getCatContentId() == 1L)
        .extracting("imageUri")
        .containsAll(
            List.of(
                "https://image.itthatcat.xyz/0cb9b066-5d37-4788-8695-7fd929e06c21.jpg",
                "https://image.itthatcat.xyz/0cb9b066-5d37-4788-8695-7fd929e06c22.jpg",
                "https://image.itthatcat.xyz/0cb9b066-5d37-4788-8695-7fd929e06c23.jpg"
            )
        );
  }


  private void saveMember() {
    userRepository.saveAndFlush(activeUser());
  }

  private String userToken(long id) {
    return tokenProvider.issueAccessToken(id);
  }

  private User activeUser() {
    User user = User.builder()
        .id(1L)
        .userOAuthProvider(UserOAuthProvider.KAKAO)
        .oAuthUid("oauth-uid")
        .build();
    user.updateNickname("nickname");
    return user;
  }

  @Test
  void issueAccessToken() {
    System.out.println(userToken(1L));
  }
}
