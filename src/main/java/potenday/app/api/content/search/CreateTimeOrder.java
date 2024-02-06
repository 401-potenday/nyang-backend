package potenday.app.api.content.search;

import java.util.Arrays;

public enum CreateTimeOrder {
  ASC, DESC;

  private static final CreateTimeOrder[] values = values();

  public static CreateTimeOrder from(String queryValue) {
    return Arrays.stream(values)
        .filter(it -> it.name().equalsIgnoreCase(queryValue))
        .findFirst()
        .orElse(DESC);
  }
}
