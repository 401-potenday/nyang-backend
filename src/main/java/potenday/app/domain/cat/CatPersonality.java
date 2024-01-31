package potenday.app.domain.cat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.exception.ExternalException;

public enum CatPersonality {
  LIKES_PEOPLE("사람을 좋아해요"),
  LOVES_TO_CUDDLE("애교가 많아요"),
  HUNTER_INSTINCT("사냥 습성이 있어요"),
  CURIOUS("호기심이 많아요"),
  SENSITIVE("예민해요"),
  GUARDED("경계가 심해요"),
  GETS_ALONG_WITH_CATS("고양이들과 잘 지내요"),
  UNSURE("모르겠어요");

  private final String description;

  public static final List<CatPersonality> values = Arrays.stream(values()).collect(Collectors.toList());

  CatPersonality(String description) {
    this.description = description;
  }

  public static CatPersonality from(String catPersonalityCode) {
    return values.stream()
        .filter(power -> power.name().equals(catPersonalityCode))
        .findFirst()
        .orElseThrow(() -> new ExternalException(ErrorCode.C001));
  }
}
