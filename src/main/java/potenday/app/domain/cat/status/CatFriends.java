package potenday.app.domain.cat.status;

import java.util.Arrays;
import java.util.List;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.exception.ExternalException;

public enum CatFriends {
  YES("yes"), NO("no"), UNSURE("unsure");

  private final String desc;

  private static final List<CatFriends> values = Arrays.stream(values()).toList();

  CatFriends(String desc) {
    this.desc = desc;
  }

  public static CatFriends from(String desc) {
    return values.stream().filter(it -> it.desc.equals(desc.toLowerCase().trim())).findFirst()
        .orElseThrow(() -> new ExternalException(ErrorCode.C002));
  }
}
