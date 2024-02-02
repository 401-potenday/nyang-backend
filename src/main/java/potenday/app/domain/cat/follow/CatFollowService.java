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

  private final CatFollowRespository catFollowRespository;
  private final UserRepository userRepository;
  private final CatContentRepository catContentRepository;

  public CatFollowService(CatFollowRespository catFollowRespository, UserRepository userRepository,
      CatContentRepository catContentRepository) {
    this.catFollowRespository = catFollowRespository;
    this.userRepository = userRepository;
    this.catContentRepository = catContentRepository;
  }

  @Transactional
  public void catfollow(AppUser appUser, AddCatFollow addCatFollow) {
    User user = findUser(appUser);
    CatContent catContent = findContent(addCatFollow);
    catFollowRespository.save(new CatFollow(user.getId(), catContent.getUserId()));
  }

  private CatContent findContent(AddCatFollow addCatFollow) {
    CatContent catContent = catContentRepository.findById(addCatFollow.catContentId())
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
