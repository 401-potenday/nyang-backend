package potenday.app.api.content.search;

import java.util.Arrays;

public enum DistanceOrder {
  ASC, DESC;

  private static final DistanceOrder[] values = values();

  public static DistanceOrder from(String queryValue) {
    return Arrays.stream(values)
        .filter(it -> it.name().equalsIgnoreCase(queryValue))
        .findFirst()
        .orElse(null);
  }
}
