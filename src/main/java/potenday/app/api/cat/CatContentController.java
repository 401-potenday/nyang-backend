package potenday.app.api.cat;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import potenday.app.api.common.ApiResponse;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.auth.AuthenticationPrincipal;
import potenday.app.domain.cat.AddCatContentService;

@RestController
public class CatContentController {

  private final AddCatContentService addCatContentService;

  public CatContentController(AddCatContentService addCatContentService) {
    this.addCatContentService = addCatContentService;
  }

  @PostMapping("/contents")
  public ApiResponse<AddCatContentResponse> addCatContent(
      @AuthenticationPrincipal AppUser appUser,
      @Valid @RequestBody AddCatContentRequest catContentRequest
  ) {
    long contentId = addCatContentService.addContent(
        appUser,
        catContentRequest.toAddCatContent(),
        catContentRequest.toAddCatImages()
    );
    return ApiResponse.success(new AddCatContentResponse(contentId));
  }
}
