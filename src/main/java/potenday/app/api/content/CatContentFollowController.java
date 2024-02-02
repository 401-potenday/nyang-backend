package potenday.app.api.content;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import potenday.app.api.common.ApiResponse;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.auth.AuthenticationPrincipal;
import potenday.app.domain.cat.follow.CatFollowService;

@RestController
public class CatContentFollowController {

  private final CatFollowService catFollowService;

  public CatContentFollowController(CatFollowService catFollowService) {
    this.catFollowService = catFollowService;
  }

  @PostMapping("/contents/follow")
  public ApiResponse<String> catFollow(
      @AuthenticationPrincipal AppUser appUser,
      @Valid @RequestBody CatFollowAndCancelRequest catFollowAndCancelRequest
  ) {
    System.out.println("     catFollowAndCancelRequest.getContentId() :  " +     catFollowAndCancelRequest.getContentId());
    catFollowService.catFollow(appUser, catFollowAndCancelRequest.toAddFollow());
    return ApiResponse.success("ok");
  }

  @DeleteMapping("/contents/follow")
  public ApiResponse<?> catFollowCancel(
      @AuthenticationPrincipal AppUser appUser,
      @Valid @RequestBody CatFollowAndCancelRequest catFollowAndCancelRequest
  ) {
    catFollowService.cancelCatFollow(appUser, catFollowAndCancelRequest.toCancelFollow());
    return ApiResponse.success("ok");
  }
}
