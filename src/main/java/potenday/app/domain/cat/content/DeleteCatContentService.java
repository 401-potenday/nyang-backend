package potenday.app.domain.cat.content;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import potenday.app.domain.auth.AppUser;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;
import potenday.app.query.repository.CatCommentQuery;

@Service
public class DeleteCatContentService {

  private final CatContentRepository catContentRepository;
  private final CatCommentQuery catCommentQuery;

  public DeleteCatContentService(CatContentRepository catContentRepository,
      CatCommentQuery catCommentQuery) {
    this.catContentRepository = catContentRepository;
    this.catCommentQuery = catCommentQuery;
  }

  @Transactional
  public void deleteContent(AppUser appUser, long contentId) {
    CatContent catContent = findContent(contentId);
    checkContentOwner(appUser, catContent);
    checkDeleted(catContent);

    if (hasLeastOneComment(contentId)) {
      catContent.setMark();
      return;
    }

    catContent.setDeleted();
  }

  private boolean hasLeastOneComment(long contentId) {
    long comments = catCommentQuery.countContentComments(contentId);
    return comments > 0;
  }

  private void checkDeleted(CatContent catContent) {
    if (catContent.isDeleted() || catContent.isMarked()) {
      throw new PotendayException(ErrorCode.C017);
    }
  }

  private CatContent findContent(long contentId) {
    return catContentRepository.findById(contentId)
        .orElseThrow(() -> new PotendayException(ErrorCode.C004));
  }

  private void checkContentOwner(AppUser appUser, CatContent catContent) {
    if (catContent.getUserId() != appUser.id()) {
      throw new PotendayException(ErrorCode.A002);
    }
  }
}
