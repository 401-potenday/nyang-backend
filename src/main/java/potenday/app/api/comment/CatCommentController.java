package potenday.app.api.comment;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import potenday.app.api.common.ApiResponse;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.auth.AuthenticationPrincipal;
import potenday.app.domain.cat.comment.CatCommentService;
import potenday.app.domain.cat.commentlikes.CatCommentLikeService;

@RestController
public class CatCommentController {

  private final CatCommentService catCommentService;
  private final CatCommentLikeService catCommentLikeService;

  public CatCommentController(CatCommentService catCommentService,
      CatCommentLikeService catCommentLikeService) {
    this.catCommentService = catCommentService;
    this.catCommentLikeService = catCommentLikeService;
  }

  @PostMapping("/contents/{contentId}/comments")
  public ApiResponse<AddCatCommentResponse> addComment(
      @AuthenticationPrincipal AppUser appUser,
      @PathVariable long contentId,
      @Valid  @RequestBody AddCatCommentRequest addCatCommentRequest
  ) {
    long addedCommentId = catCommentService.addComment(
        appUser,
        addCatCommentRequest.toAddCatComment(contentId),
        addCatCommentRequest.toAddCatCommentImages()
    );
    return ApiResponse.success(new AddCatCommentResponse(addedCommentId));
  }

  @PostMapping("/contents/{contentId}/comments/likes")
  public ApiResponse<String> addCommentLike(
      @AuthenticationPrincipal AppUser appUser,
      @PathVariable long contentId,
      @Valid @RequestBody AddCatCommentLikeRequest addCatCommentLikeRequest
  ) {
    catCommentLikeService.addCommentLike(appUser, addCatCommentLikeRequest.toAddCatComment(contentId));
    return ApiResponse.success("ok");
  }
}
