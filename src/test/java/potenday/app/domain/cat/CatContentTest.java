package potenday.app.domain.cat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CatContentTest {

  @Test
  @DisplayName("Builer 를 이용해서 CatContent 객체 생성 - 성공")
  void builder() {
    // create builder
    CatContent catContent = CatContent.builder()
        .id(1L)
        .name("Fluffy")
        .hasFriends("Y")
        .description("Fluffy is a cute and cuddly cat.")
        .personality("Fluffy is always up for a playful nap.")
        .lat(37.7749)
        .lon(122.4194)
        .roadAddress("123 Main St")
        .jibunAddrName("Jibun Address Name")
        .jibunMainAddrNo("123")
        .jibunSido("Seoul")
        .jibunSigungu("Seoul")
        .jibunDong("Jongmyeong")
        .jibunSubAddrNo("456")
        .neuter("UNSURE")
        .build();

    // check values
    assertEquals(1L, catContent.getId());
    assertEquals("Fluffy", catContent.getName());
    assertEquals("Y", catContent.getHasFriends());
    assertEquals("Fluffy is a cute and cuddly cat.", catContent.getDescription());
    assertEquals("Fluffy is always up for a playful nap.", catContent.getPersonality());
    assertEquals(37.7749, catContent.getLat());
    assertEquals(122.4194, catContent.getLon());
    assertEquals("123 Main St", catContent.getRoadAddress());
    assertEquals("Jibun Address Name", catContent.getJibunAddrName());
    assertEquals("123", catContent.getJibunMainAddrNo());
    assertEquals("Seoul", catContent.getJibunSido());
    assertEquals("Seoul", catContent.getJibunSigungu());
    assertEquals("Jongmyeong", catContent.getJibunDong());
    assertEquals("456", catContent.getJibunSubAddrNo());
    assertEquals("UNSURE", catContent.getNeuter());
  }

}