package potenday.app.domain.user;

import java.util.Arrays;

public enum UserOAuthProvider {
  NAVER("naver"), KAKAO("kakao");

  private final String name;
  UserOAuthProvider(String name) {
    this.name = name;
  }

  public static UserOAuthProvider from(String provider) {
    return Arrays.stream(values()).filter(it -> it.name.equals(provider))
        .findFirst()
        .orElseThrow();
  }
}
