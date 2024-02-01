package potenday.app.api.cat;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import potenday.app.api.common.ApiResponse;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.auth.AuthenticationPrincipal;
import potenday.app.domain.auth.OptionalAuthenticationPrincipal;
import potenday.app.domain.cat.AddCatContentService;
import potenday.app.query.model.CatContentDetails;
import potenday.app.query.service.ReadCatContentService;

@RestController
public class CatContentController {

  private final AddCatContentService addCatContentService;
  private final ReadCatContentService readCatContentService;

  public CatContentController(AddCatContentService addCatContentService,
      ReadCatContentService readCatContentService) {
    this.addCatContentService = addCatContentService;
    this.readCatContentService = readCatContentService;
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

  @GetMapping("/contents/{contentId}")
  public ApiResponse<CatContentDetails> getCatContents(
      @OptionalAuthenticationPrincipal AppUser appUser,
      @PathVariable long contentId) {
    if (appUser == null) {
      return ApiResponse.success(readCatContentService.fetchContentDetails(contentId));
    }
    System.out.println(appUser.id());
    return ApiResponse.success(readCatContentService.fetchContentDetails(contentId));
  }
}
