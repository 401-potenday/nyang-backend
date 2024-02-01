package potenday.app.domain.cat.status;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// 고양이 성격 관리하는 클래스
// List<String> personalities => List<Personality> 으로 변경 필요
// A, B, C => List<Personality> 으로 변경 필요
public class CatPersonalities {

  private final Set<CatPersonality> personalities;

  private CatPersonalities(List<String> personalities) {
    this.personalities = personalities.stream()
        .map(CatPersonality::from)
        .collect(Collectors.toSet());
  }

  public static CatPersonalities of(List<String> personalities) {
    return new CatPersonalities(personalities);
  }

  public String concatenateCatPersonalitiesWithComma() {
    return personalities.stream()
        .map(CatPersonality::toString)
        .collect(Collectors.joining(", "));
  }

  public Set<CatPersonality> getPersonalities() {
    return personalities;
  }

}
