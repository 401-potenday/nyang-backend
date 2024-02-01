package potenday.app.domain.cat.status;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CatPersonalitiesTest {

  @Test
  @DisplayName("String 성격 코드 리스트를 이용해 실제 성격 코드가 담신 CatPersonalities 를 생성한다. - 성공")
  void create() {
    CatPersonalities catPersonalities = CatPersonalities.of(
        List.of("LIKES_PEOPLE", "LOVES_TO_CUDDLE"));

    assertThat(catPersonalities.getPersonalities().size()).isEqualTo(2);
    assertThat(catPersonalities.getPersonalities()).contains(CatPersonality.LIKES_PEOPLE,
        CatPersonality.LOVES_TO_CUDDLE);
  }

  @Test
  @DisplayName("CatPersonalities 에 담긴 성격 코드 리스트를 이용해 성격 코드를 문자열로 변환한다. - 성공")
  void getPersonalitiesAsString() {
    // given
    CatPersonalities catPersonalities = CatPersonalities.of(
        List.of("LIKES_PEOPLE", "LOVES_TO_CUDDLE"));

    // when
    String personalitiesWithComma = catPersonalities.concatenateCatPersonalitiesWithComma();

    // then
    assertThat(personalitiesWithComma).isIn("LIKES_PEOPLE, LOVES_TO_CUDDLE",
        "LOVES_TO_CUDDLE, LIKES_PEOPLE");

  }

  @Test
  @DisplayName("같은 요소의 성격이 있는 지 확인한다. - 성공")
  void isEqualIfHasSameElements() {
    CatPersonalities catPersonalities = CatPersonalities.of(List.of("LIKES_PEOPLE", "LOVES_TO_CUDDLE"));
    CatPersonalities catPersonalities2 = CatPersonalities.of(List.of("LIKES_PEOPLE", "LOVES_TO_CUDDLE"));

    assertThat(catPersonalities).isEqualTo(catPersonalities2);
    }
  }
