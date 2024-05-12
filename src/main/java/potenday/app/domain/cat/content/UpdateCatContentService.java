package potenday.app.domain.cat.content;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potenday.app.api.content.UpdateCatContentImages;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.report.CatContentReportRepository;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserRepository;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;

@Service
public class UpdateCatContentService {

  private final CatContentRepository catContentRepository;
  private final UserRepository userRepository;
  private final CatContentImageRepository catContentImageRepository;
  private final CatContentReportRepository catContentReportRepository;

  public UpdateCatContentService(CatContentRepository catContentRepository,
      UserRepository userRepository, CatContentImageRepository catContentImageRepository,
      CatContentReportRepository catContentReportRepository) {
    this.catContentRepository = catContentRepository;
    this.userRepository = userRepository;
    this.catContentImageRepository = catContentImageRepository;
    this.catContentReportRepository = catContentReportRepository;
  }

  @Transactional
  public void updateContent(AppUser appUser, long contentId, UpdateCatContent updateCatContent, UpdateCatContentImages updateCatImages) {
    findUser(appUser);
    CatContent catContent = findUpdatableContent(contentId, appUser);
    checkReportedContent(contentId);
    catContent.updateFrom(updateCatContent);
    catContentImageRepository.deleteAllByCatContentId(contentId);
    catContentImageRepository.saveAll(createCatContentImages(catContent.getId(), updateCatImages));
  }

  private void checkReportedContent(long contentId) {
    if (isNotAccessibleReportedContent(contentId)) {
      throw new PotendayException(ErrorCode.A002);
    }
  }

  private boolean isNotAccessibleReportedContent(long contentId) {
    return catContentReportRepository.findPendingOrCompletedReportByContentId(contentId);
  }

  private List<CatContentImage> createCatContentImages(Long contentId, UpdateCatContentImages addCatContentImages) {
    CatContentImages contentImages = addCatContentImages.toContentImages();
    return contentImages.toTargetImages(contentId);
  }

  private CatContent findUpdatableContent(long contentId, AppUser appUser) {
    CatContent catContent = catContentRepository.findById(contentId)
        .orElseThrow(() -> new PotendayException(ErrorCode.C004));

    if (catContent.isDeleted()) {
      throw new PotendayException(ErrorCode.C004);
    }

    if (!catContent.isOwner(appUser)) {
      throw new PotendayException(ErrorCode.A004);
    }

    return catContent;
  }

  private User findUser(AppUser appUser) {
    User user = userRepository.findById(appUser.id())
        .orElseThrow(() -> new PotendayException(ErrorCode.A001));
    if (!user.isActive()) {
      throw new PotendayException(ErrorCode.A001);
    }
    return user;
  }
}
