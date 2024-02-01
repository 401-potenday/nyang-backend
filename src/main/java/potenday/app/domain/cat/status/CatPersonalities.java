package potenday.app.domain.cat.status;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// 고양이 성격 관리하는 클래스
public class CatPersonalities {

  private final Set<CatPersonality> personalities;
  public static final CatPersonality DEFAULT_PERSONALITY = CatPersonality.UNSURE;

  private CatPersonalities(List<String> personalities) {
    this.personalities = personalities.stream()
        .map(CatPersonality::from)
        .collect(Collectors.toSet());
  }

  public static CatPersonalities of(List<String> personalities) {
    return new CatPersonalities(personalities);
  }

  public static CatPersonalities fromSting(String personalities) {
    return new CatPersonalities(List.of(personalities.split(", ")));
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
