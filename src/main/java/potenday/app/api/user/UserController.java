package potenday.app.api.user;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import potenday.app.api.common.ApiResponse;
import potenday.app.api.validation.NicknameValidationSequence;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.auth.AuthenticationPrincipal;
import potenday.app.domain.user.UserService;

@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/user/nickname/available-check")
  public ApiResponse<NicknameAvailableResponse> checkDuplicate(
      @Validated(value = NicknameValidationSequence.class)
      @RequestBody UserNicknameRequest userNicknameRequest
  ) {
    boolean isAvailable = userService.checkAvailableNickname(userNicknameRequest.getNickname());
    return ApiResponse.success(new NicknameAvailableResponse(isAvailable));
  }

  @PostMapping("/user/nickname")
  public ApiResponse<UserNicknameResponse> registerNickname(
      @AuthenticationPrincipal AppUser appUser,
      @Validated(value = NicknameValidationSequence.class) @RequestBody UserNicknameRequest userNicknameRequest
  ) {
    String registeredNickname = userService.registerNickname(appUser,
        userNicknameRequest.toNickname());
    return ApiResponse.success(new UserNicknameResponse(registeredNickname));
  }

  @PutMapping("/user/nickname")
  public ApiResponse<UserNicknameResponse> changeNickname(
      @AuthenticationPrincipal AppUser appUser,
      @Validated(value = NicknameValidationSequence.class) @RequestBody UserNicknameRequest userNicknameRequest
  ) {
    userService.changeNickName(appUser, userNicknameRequest.toNickname());
    return ApiResponse.success();
  }

  @GetMapping("/user/nickname")
  public ApiResponse<UserNicknameResponse> getNickname(@AuthenticationPrincipal AppUser appUser) {
    String userNickname = userService.getUserNickname(appUser);
    return ApiResponse.success(new UserNicknameResponse(userNickname));
  }
}
