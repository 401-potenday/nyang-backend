package potenday.app.api.comment;

import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import potenday.app.api.common.ApiResponse;
import potenday.app.api.common.PageContent;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.auth.AuthenticationPrincipal;
import potenday.app.domain.auth.OptionalAuthenticationPrincipal;
import potenday.app.domain.cat.comment.CatCommentService;
import potenday.app.domain.cat.commentlikes.CatCommentLikeService;
import potenday.app.domain.cat.support.CatCommentEngagementsCalculator;
import potenday.app.query.model.comment.CatCommentWithUserNicknameAndImages;
import potenday.app.query.service.ReadCatCommentService;

@Slf4j
@RestController
public class CatCommentController {

  private final CatCommentService catCommentService;
  private final CatCommentLikeService catCommentLikeService;
  private final ReadCatCommentService readCatCommentService;
  private final CatCommentEngagementsCalculator catCommentEngagementsCalculator;

  public CatCommentController(CatCommentService catCommentService,
      CatCommentLikeService catCommentLikeService, ReadCatCommentService readCatCommentService,
      CatCommentEngagementsCalculator catCommentEngagementsCalculator) {
    this.catCommentService = catCommentService;
    this.catCommentLikeService = catCommentLikeService;
    this.readCatCommentService = readCatCommentService;
    this.catCommentEngagementsCalculator = catCommentEngagementsCalculator;
  }

  @GetMapping("/contents/{contentId}/comments")
  public ApiResponse<PageContent<CatCommentResponse>> getComments(
      @OptionalAuthenticationPrincipal AppUser appUser,
      @PathVariable long contentId,
      @PageableDefault(page = 1) Pageable pageable
  ) {
    PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
    var catCommentWithUserNicknameAndImages = readCatCommentService.findCatComments(contentId, pageRequest);

    List<CatCommentResponse> catCommentResponses = catCommentWithUserNicknameAndImages.stream()
        .map(it -> {
          long commentLikesCount = catCommentEngagementsCalculator.getCommentLikesCount(it.catCommentId());
          if (appUser != null) {
            boolean commentLiked = catCommentEngagementsCalculator.isCommentLiked(appUser.id(), it.catCommentId());
            return CatCommentResponse.of(it, commentLikesCount, commentLiked);
          }
          return CatCommentResponse.of(it, commentLikesCount, false);
        })
        .toList();

    // createResponse
    PageContent<CatCommentResponse> pageContent = new PageContent<>(
        catCommentResponses,
        catCommentWithUserNicknameAndImages.getPageable().getPageNumber(),
        catCommentWithUserNicknameAndImages.getPageable().getPageSize(),
        catCommentWithUserNicknameAndImages.getTotalPages(),
        catCommentWithUserNicknameAndImages.getTotalElements(),
        catCommentWithUserNicknameAndImages.isLast()
    );

    return ApiResponse.success(pageContent);
  }

  private boolean isCommentLiked(AppUser appUser, CatCommentWithUserNicknameAndImages it) {
    return catCommentEngagementsCalculator.isCommentLiked(appUser.id(), it.catCommentId());
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

  @DeleteMapping("/contents/{contentId}/comments/likes")
  public ApiResponse<String> cancelCommentLike(
      @AuthenticationPrincipal AppUser appUser,
      @PathVariable long contentId,
      @Valid @RequestBody CancelCommentLikeRequest cancelCommentLikeRequest
  ) {
    catCommentLikeService.cancelCommentLike(appUser, cancelCommentLikeRequest.toCancelComment(contentId));
    return ApiResponse.success("ok");
  }
}
