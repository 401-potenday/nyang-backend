package potenday.app.domain.cat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import potenday.app.domain.cat.content.CatContent;
import potenday.app.domain.cat.status.CatFriends;
import potenday.app.domain.cat.status.CatNeuter;
import potenday.app.domain.cat.status.CatPersonalities;

class CatContentTest {

  @Test
  @DisplayName("Builer 를 이용해서 CatContent 객체 생성 - 성공")
  void builder() {
    CatContent catContent = CatContent.builder()
        .id(1L)
        .name("Fluffy")
        .hasFriends(CatFriends.YES)
        .description("Fluffy is a cute and cuddly cat.")
        .catPersonalities(CatPersonalities.of(List.of("LIKES_PEOPLE")))
        .neuter(CatNeuter.UNSURE)
        .build();

    assertEquals(1L, catContent.getId());
    assertEquals("Fluffy", catContent.getName());
    assertEquals(CatFriends.YES, catContent.getHasFriends());
    assertEquals("Fluffy is a cute and cuddly cat.", catContent.getDescription());
    assertEquals(CatPersonalities.of(List.of("LIKES_PEOPLE")), catContent.getCatPersonalities());
    assertEquals(CatNeuter.UNSURE, catContent.getNeuter());
  }

}