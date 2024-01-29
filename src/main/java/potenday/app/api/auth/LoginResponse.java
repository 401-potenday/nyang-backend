package potenday.app.api.auth;

public record LoginResponse(
    String accessToken, String refreshToken, String nickname
) {

}
