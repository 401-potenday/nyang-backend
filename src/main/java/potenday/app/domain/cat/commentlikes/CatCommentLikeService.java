package potenday.app.domain.cat.commentlikes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.cat.comment.CatComment;
import potenday.app.domain.cat.comment.CatCommentRepository;
import potenday.app.domain.user.User;
import potenday.app.domain.user.UserRepository;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.PotendayException;

@Service
public class CatCommentLikeService {

  private final CatCommentLikeRepository catCommentLikeRepository;
  private final CatCommentRepository catCommentRepository;
  private final UserRepository userRepository;

  public CatCommentLikeService(CatCommentLikeRepository catCommentLikeRepository,
      CatCommentRepository catCommentRepository,
      UserRepository userRepository) {
    this.catCommentLikeRepository = catCommentLikeRepository;
    this.catCommentRepository = catCommentRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public void addCommentLike(AppUser appUser, AddCatCommentLike addCatCommentLike) {
    User user = findUser(appUser);
    CatComment catComment = findComment(addCatCommentLike.commentId());
    CatCommentLikeId commentLikeId = createCommentId(user.getId(), catComment.getId());
    if (isCommentLiked(commentLikeId)) {
      throw new PotendayException(ErrorCode.C009);
    }
    CatCommentLike catCommentLike = new CatCommentLike(commentLikeId);
    saveLike(catCommentLike);
  }

  @Transactional
  public void cancelCommentLike(AppUser appUser, CancelCommentLike cancelCommentLike) {
    User user = findUser(appUser);
    CatComment catComment = findComment(cancelCommentLike.commentId());
    CatCommentLikeId commentLikeId = createCommentId(user.getId(), catComment.getId());
    if (!isCommentLiked(commentLikeId)) {
      return;
    }
    catCommentLikeRepository.deleteByCatCommentLikeId(commentLikeId);
  }

  private void saveLike(CatCommentLike catCommentLike) {
    catCommentLikeRepository.save(catCommentLike);
  }

  private CatComment findComment(long commentId) {
    return catCommentRepository.findById(commentId)
        .orElseThrow(() -> new PotendayException(ErrorCode.D003));
  }

  private static CatCommentLikeId createCommentId(long userId, long commentId) {
    return new CatCommentLikeId(userId, commentId);
  }

  private boolean isCommentLiked(CatCommentLikeId catCommentLikeId) {
    return catCommentLikeRepository.existsByCatCommentLikeId(catCommentLikeId);
  }

  private User findUser(AppUser appUser) {
    User user = userRepository.findById(appUser.id())
        .orElseThrow(() -> new PotendayException(ErrorCode.A004));
    user.authorizationCheck();
    return user;
  }
}
