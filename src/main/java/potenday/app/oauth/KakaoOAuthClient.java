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

@Component
public class KakaoOAuthClient implements OAuthClient{

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
    OAuthTokenResponse oAuthTokenResponse = getOAuthToken(tokenRequest.getCode());
    return getOAuthMember(oAuthTokenResponse.getAccessToken());
  }

  @Override
  public OAuthTokenResponse getOAuthToken(String code) {
    // set header
    HttpHeaders headers = tokenRequestHeaders();

    // set body
    MultiValueMap<String, String> map = tokenRequestBody(code);

    // request
    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

    // response
    ResponseEntity<OAuthTokenResponse> response = tokenRequest(requestEntity);
    return response.getBody();
  }

  private ResponseEntity<OAuthTokenResponse> tokenRequest(HttpEntity<MultiValueMap<String, String>> requestEntity) {
    String tokenIssueUri = kakaoProperties.getOauthTokenIssueUri();
    return restTemplate.postForEntity(tokenIssueUri, requestEntity, OAuthTokenResponse.class);
  }

  private HttpHeaders tokenRequestHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    return headers;
  }

  private MultiValueMap<String, String> tokenRequestBody(String code) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("grant_type", "authorization_code");
    map.add("client_id", kakaoProperties.getClientId());
    map.add("redirect_uri", kakaoProperties.getTokenIssueUri());
    map.add("code", code);
    return map;
  }

  @Override
  public OAuthMember getOAuthMember(String accessToken) {
    // set header
    HttpHeaders headers = oAuthUserRequestHeader(accessToken);

    // set body
    MultiValueMap<String, String> body = oAuthUserRequestBody();

    // request
    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

    // response
    ResponseEntity<String> response = userRequest(requestEntity);
    return createOAuthMember(response);
  }

  private OAuthMember createOAuthMember(ResponseEntity<String> response) {
    try {
      JsonNode jsonNode = objectMapper.readTree(response.getBody());
      long id = jsonNode.get("id").asLong();

      return OAuthMember.builder()
          .id(id)
          .build();

    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private ResponseEntity<String> userRequest(
      HttpEntity<MultiValueMap<String, String>> requestEntity) {
    String url = kakaoProperties.getOauthUserInfoUri();
    return restTemplate.postForEntity(url, requestEntity, String.class);
  }

  private static MultiValueMap<String, String> oAuthUserRequestBody() {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("property_keys", "[\"kakao_account.email\"]");
    return map;
  }

  private static HttpHeaders oAuthUserRequestHeader(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.set("Authorization", "Bearer " + accessToken);
    return headers;
  }
}
