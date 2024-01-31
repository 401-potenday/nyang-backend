package potenday.app.domain.cat.status;

import java.util.Arrays;
import java.util.List;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.exception.ExternalException;

public enum CatNeuter {
  YES("yes"), NO("no"), UNSURE("unsure");

  private final String desc;

  private static final List<CatNeuter> values = Arrays.stream(values()).toList();

  CatNeuter(String desc) {
    this.desc = desc;
  }

  public static CatNeuter from(String desc) {
    return values.stream().filter(it -> it.desc.equals(desc.toLowerCase().trim())).findFirst()
        .orElseThrow(() -> new ExternalException(ErrorCode.C002));
  }
}
