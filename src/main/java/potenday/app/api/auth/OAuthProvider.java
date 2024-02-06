package potenday.app.api.auth;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;

public enum OAuthProvider {
  KAKAO("kakao");

  private static final Map<String, OAuthProvider> oathProviders =
      Arrays.stream(values()).collect(Collectors.toMap(provider -> provider.providerName, provider -> provider));

  private final String providerName;

  OAuthProvider(String providerName) {
    this.providerName = providerName;
  }

  public static OAuthProvider of(String provider) {
    OAuthProvider result = oathProviders.get(provider);
    if (result == null) {
      throw new PotendayException(ErrorCode.L001);
    }
    return result;
  }
}
