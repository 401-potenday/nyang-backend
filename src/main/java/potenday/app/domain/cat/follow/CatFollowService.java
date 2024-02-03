package potenday.app.domain.cat.follow;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.cat.content.CatContent;
import potenday.app.domain.cat.content.CatContentRepository;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserRepository;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;

@Service
public class CatFollowService {

  private final CatFollowRepository catFollowRepository;
  private final UserRepository userRepository;
  private final CatContentRepository catContentRepository;

  public CatFollowService(CatFollowRepository catFollowRepository, UserRepository userRepository,
      CatContentRepository catContentRepository) {
    this.catFollowRepository = catFollowRepository;
    this.userRepository = userRepository;
    this.catContentRepository = catContentRepository;
  }

  public void catFollow(AppUser appUser, AddCatFollow addCatFollow) {
    if (isFollowed(appUser, addCatFollow.catContentId())) {
      throw new PotendayException(ErrorCode.F001);
    }
    User user = findUser(appUser);
    CatContent catContent = findContent(addCatFollow.catContentId());
    saveFollow(user, catContent);
  }

  @Transactional
  public void cancelCatFollow(AppUser appUser, UnFollow unFollow) {
    User user = findUser(appUser);
    CatContent catContent = findContent(unFollow.catContentId());
    if (isFollowed(appUser, unFollow.catContentId())) {
      deleteFollow(user, catContent);
    }
  }

  private void saveFollow(User user, CatContent catContent) {
    catFollowRepository.save(new CatFollow(user.getId(), catContent.getId()));
  }

  private void deleteFollow(User user, CatContent catContent) {
    catFollowRepository.deleteByUserIdAndCatContentId(user.getId(), catContent.getId());
  }

  private boolean isFollowed(AppUser appUser, long contentId) {
    return catFollowRepository.existsCatFollowByUserIdAndCatContentId(appUser.id(), contentId);
  }

  private CatContent findContent(long contentId) {
    CatContent catContent = catContentRepository.findById(contentId)
        .orElseThrow(() -> new PotendayException(ErrorCode.C004));
    if (catContent.isDeleted()) {
      throw new PotendayException(ErrorCode.C004);
    }
    return catContent;
  }

  private User findUser(AppUser appUser) {
    User user = userRepository.findById(appUser.id())
        .orElseThrow(() -> new PotendayException(ErrorCode.A004));
    user.authorizationCheck();
    return user;
  }
}
