package potenday.app.query.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potenday.app.domain.cat.CatContent;
import potenday.app.domain.image.CatContentImage;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;
import potenday.app.query.model.CatContentDetails;
import potenday.app.query.model.user.UserNickname;
import potenday.app.query.repository.CatContentQuery;
import potenday.app.query.repository.UserQuery;

@Service
public class ReadCatContentService {

  private final CatContentQuery catContentQuery;
  private final UserQuery userQuery;

  public ReadCatContentService(CatContentQuery catContentQuery, UserQuery userQuery) {
    this.catContentQuery = catContentQuery;
    this.userQuery = userQuery;
  }

  @Transactional(readOnly = true)
  public CatContentDetails fetchContentDetails(long contentId) {
    CatContent catContent = catContentQuery.fetchContent(contentId);
    if (catContent == null) {
      throw new PotendayException(ErrorCode.C004);
    }
    List<CatContentImage> catContentImages = catContentQuery.fetchContentImages(contentId);
    UserNickname userNickname = userQuery.fetchUserSimpleInfo(catContent.getUserId());
    return CatContentDetails.of(catContent, catContentImages, userNickname);
  }

  @Transactional(readOnly = true)
  public CatContentDetails fetchContentDetails(long contentId, long id) {
    CatContent catContent = catContentQuery.fetchContent(contentId);
    if (catContent == null) {
      throw new PotendayException(ErrorCode.C004);
    }
    List<CatContentImage> catContentImages = catContentQuery.fetchContentImages(contentId);
    UserNickname userNickname = userQuery.fetchUserSimpleInfo(catContent.getUserId());
    return CatContentDetails.of(catContent, catContentImages, userNickname);
  }
}
