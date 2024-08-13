package potenday.app.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import potenday.app.api.auth.TokenRequest;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;

@Component
public class KakaoOAuthClient implements OAuthClient {

  private final KakaoProperties kakaoProperties;
  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  public KakaoOAuthClient(KakaoProperties kakaoProperties, RestTemplateBuilder restTemplate,
      ObjectMapper objectMapper) {
    this.kakaoProperties = kakaoProperties;
    this.restTemplate = restTemplate.build();
    this.objectMapper = objectMapper;
  }

  @Override
  public OAuthMember findOAuthMember(TokenRequest tokenRequest) {
    OAuthToken oAuthToken = getKakaoToken(tokenRequest.getCode(), tokenRequest.getRedirectUri());
    return findKakaoMember(oAuthToken);
  }

  private OAuthMember findKakaoMember(OAuthToken oAuthToken){
    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(
        kakaoUserRequestBody(), kakaoUserRequestHeader(oAuthToken.getAccessToken()));
    ResponseEntity<String> response = kakaoUserRequest(httpEntity);
    try {
      JsonNode jsonNode = objectMapper.readTree(response.getBody());
      long id = jsonNode.get("id").asLong();
      return OAuthMember.from(String.valueOf(id), oAuthToken.getRefreshToken());
    } catch (JsonProcessingException e) {
      throw new PotendayException(ErrorCode.L002);
    }
  }

  @Override
  public OAuthToken getToken(String code, String redirectUri) {
    return getKakaoToken(code, redirectUri);
  }


  private OAuthToken getKakaoToken(String code, String redirectUri) {
    // set header
    HttpHeaders headers = tokenRequestHeaders();

    // set body
    MultiValueMap<String, String> map = tokenRequestBody(code, redirectUri);

    // request
    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

    // response
    ResponseEntity<OAuthToken> response = tokenRequest(requestEntity);
    return response.getBody();
  }

  private ResponseEntity<OAuthToken> tokenRequest(
      HttpEntity<MultiValueMap<String, String>> requestEntity) {
    String tokenIssueUri = kakaoProperties.getOauthTokenIssueUri();
    return restTemplate.postForEntity(tokenIssueUri, requestEntity, OAuthToken.class);
  }

  private HttpHeaders tokenRequestHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    return headers;
  }

  private MultiValueMap<String, String> tokenRequestBody(String code, String redirectUri) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("grant_type", "authorization_code");
    map.add("client_id", kakaoProperties.getClientId());
    map.add("redirect_uri", redirectUri);
    map.add("code", code);
    return map;
  }

  private ResponseEntity<String> kakaoUserRequest(
      HttpEntity<MultiValueMap<String, String>> requestEntity) {
    String url = kakaoProperties.getOauthUserInfoUri();
    return restTemplate.postForEntity(url, requestEntity, String.class);
  }

  private static MultiValueMap<String, String> kakaoUserRequestBody() {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("property_keys", "[\"kakao_account.profile\"]");
    return map;
  }

  private static HttpHeaders kakaoUserRequestHeader(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.set("Authorization", "Bearer " + accessToken);
    return headers;
  }
}
