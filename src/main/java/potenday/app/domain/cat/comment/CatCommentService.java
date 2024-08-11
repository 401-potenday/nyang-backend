package potenday.app.domain.cat.comment;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potenday.app.api.comment.UpdateCatComment;
import potenday.app.api.comment.UpdateCatCommentImages;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.cat.content.CatContentRepository;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserRepository;
import potenday.app.event.action.CommentAddEvent;
import potenday.app.event.publisher.CommentEventPublisher;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;

@Service
public class CatCommentService {

  private final CatCommentRepository catCommentRepository;
  private final CatCommentImageRepository catCommentImageRepository;
  private final CatContentRepository catContentRepository;
  private final UserRepository userRepository;
  private final CommentEventPublisher commentEventPublisher;

  public CatCommentService(CatCommentRepository catCommentRepository,
      CatCommentImageRepository catCommentImageRepository,
      CatContentRepository catContentRepository, UserRepository userRepository,
      CommentEventPublisher commentEventPublisher) {
    this.catCommentRepository = catCommentRepository;
    this.catCommentImageRepository = catCommentImageRepository;
    this.catContentRepository = catContentRepository;
    this.userRepository = userRepository;
    this.commentEventPublisher = commentEventPublisher;
  }

  @Transactional
  public long addComment(AppUser appUser, AddCatComment addCatComment, AddCatCommentImages addCatCommentImages) {
    User user = findUser(appUser);
    validateContentExisted(addCatComment.contentId());
    CatComment catComment = catCommentRepository.save(createCatComment(user, addCatComment));
    catCommentImageRepository.saveAllAndFlush(createCommentImages(addCatCommentImages.toContentImages(), addCatComment.contentId(), catComment.getId(), user.getId()));
    return catComment.getId();
  }

  @Transactional
  public void deleteComment(AppUser appUser, long commentId) {
    User user = findUser(appUser);
    CatComment catComment = findOwnerComment(user, commentId);
    catComment.setDeleted();
  }

  @Transactional
  public void updateCatComment(
      AppUser appUser,
      UpdateCatComment updateCatContent,
      UpdateCatCommentImages updateCatCommentImages
  ) {
    User user = findUser(appUser);
    CatComment catComment = findOwnerComment(user, updateCatContent.commentId());
    catComment.updateFrom(updateCatContent);
    catCommentImageRepository.deleteAllByCommentId(catComment.getId());
    var commentImages = createCommentImages(
        updateCatCommentImages.toCommentImages(),
        catComment.getCatContentId(),
        catComment.getId(),
        user.getId());
    catCommentImageRepository.saveAll(commentImages);
  }

  private CatComment findOwnerComment(User user, long commentId) {
    return catCommentRepository.findUserComment(user.getId(), commentId)
        .orElseThrow(() -> new PotendayException(ErrorCode.D003));
  }

  private CatComment createCatComment(User user, AddCatComment addCatComment) {
    return addCatComment.toCommentWithOwner(user);
  }

  private void validateContentExisted(Long contentId) {
    if (!catContentRepository.existsById(contentId)) {
      throw new PotendayException(ErrorCode.C004);
    }
  }

  private User findUser(AppUser appUser) {
    User user = userRepository.findById(appUser.id())
        .orElseThrow(() -> new PotendayException(ErrorCode.A001));
    user.authorizationCheck();
    return user;
  }

  private List<CatCommentImage> createCommentImages(
      CatCommentImages commentImages,
      long contentId,
      long commentId,
      long userId
  ) {
    return commentImages.toTargetImages(contentId, commentId, userId);
  }
}
