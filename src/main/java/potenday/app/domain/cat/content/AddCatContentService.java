package potenday.app.domain.cat.content;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.image.AddCatContentImages;
import potenday.app.domain.image.CatContentImage;
import potenday.app.domain.image.CatContentImageRepository;
import potenday.app.domain.image.CatContentImages;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserRepository;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;

@Service
public class AddCatContentService {

  private final CatContentRepository catContentRepository;
  private final CatContentImageRepository catContentImageRepository;
  private final UserRepository userRepository;

  public AddCatContentService(CatContentRepository catContentRepository,
      CatContentImageRepository catContentImageRepository, UserRepository userRepository) {
    this.catContentRepository = catContentRepository;
    this.catContentImageRepository = catContentImageRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public long addContent(AppUser appUser, AddCatContent addCatcontent, AddCatContentImages addCatContentImages) {
    User user = findUser(appUser);
    CatContent savedCatContent = catContentRepository.save(createContent(user, addCatcontent));
    catContentImageRepository.saveAll(createCatContentImages(savedCatContent.getId(), addCatContentImages));
    return savedCatContent.getId();
  }

  private CatContent createContent(User user, AddCatContent addCatcontent) {
    CatContent content = addCatcontent.toContent();
    content.setOwner(user.getId());
    return content;
  }

  private List<CatContentImage> createCatContentImages(Long contentId, AddCatContentImages addCatContentImages) {
    CatContentImages contentImages = addCatContentImages.toContentImages();
    return contentImages.toTargetImages(contentId);
  }

  private User findUser(AppUser appUser) {
    User user = userRepository.findById(appUser.id())
        .orElseThrow(() -> new PotendayException(ErrorCode.A001));
    if (!user.active()) {
      throw new PotendayException(ErrorCode.A001);
    }
    return user;
  }
}
