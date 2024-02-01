package potenday.app.domain.cat.status;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

// 고양이 성격 관리하는 클래스
public class CatPersonalities {

  private final Set<CatPersonality> personalities;
  public static final CatPersonality DEFAULT_PERSONALITY = CatPersonality.UNSURE;

  public CatPersonalities(List<CatPersonality> personalities) {
    this.personalities = new HashSet<>(personalities);
  }

  public static CatPersonalities of(List<String> personalities) {
    List<CatPersonality> collect = personalities.stream()
        .map(CatPersonality::from)
        .toList();
    return new CatPersonalities(collect);
  }

  public static CatPersonalities fromSting(String personalities) {
    List<String> split = List.of(personalities.split(", "));
    return CatPersonalities.of(split);
  }


  public String concatenateCatPersonalitiesWithComma() {
    return personalities.stream()
        .map(CatPersonality::toString)
        .collect(Collectors.joining(", "));
  }

  public Set<CatPersonality> getPersonalities() {
    return personalities;
  }

  public boolean equal(CatPersonalities catPersonalities) {
    return this.personalities.containsAll(catPersonalities.personalities);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CatPersonalities that = (CatPersonalities) o;
    return this.personalities.containsAll(that.personalities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personalities);
  }
}
