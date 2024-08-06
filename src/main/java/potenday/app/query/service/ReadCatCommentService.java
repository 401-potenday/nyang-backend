package potenday.app.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potenday.app.api.comment.CatCommentResponse;
import potenday.app.api.common.ScrollContent;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.cat.comment.CatComment;
import potenday.app.global.error.ErrorCode;
import potenday.app.global.error.ErrorContent;
import potenday.app.global.error.PotendayException;
import potenday.app.query.model.comment.CatCommentInfoDto;
import potenday.app.query.model.comment.CatCommentWithIsLikedAndLikeCount;
import potenday.app.query.model.comment.CatCommentWithUserNicknameAndImages;
import potenday.app.query.repository.CatCommentQuery;
import potenday.app.query.repository.CatContentQuery;

@Service
public class ReadCatCommentService {

  private final CatCommentQuery catCommentQuery;
  private final CatContentQuery catContentQuery;

  public ReadCatCommentService(CatCommentQuery catCommentQuery, CatContentQuery catContentQuery) {
    this.catCommentQuery = catCommentQuery;
    this.catContentQuery = catContentQuery;
  }

  @Transactional(readOnly = true)
  public Page<CatCommentWithUserNicknameAndImages> findCatComments(Long contentId, Pageable pageable) {
    boolean existed = catContentQuery.existsByContentId(contentId);
    if (!existed) {
      throw new PotendayException(ErrorCode.C004);
    }
    return catCommentQuery.findAllCommentWithPaging(contentId, pageable);
  }

  @Transactional(readOnly = true)
  public ScrollContent<?> findMyCatComments(AppUser appUser, Pageable pageable) {
    Slice<CatCommentWithIsLikedAndLikeCount> userCommentsWithPaging = catCommentQuery.findUserComments(appUser.id(), pageable);
    return new ScrollContent<>(
        userCommentsWithPaging.stream()
            .map(CatCommentResponse::of)
            .toList(),
        userCommentsWithPaging.isLast(),
        userCommentsWithPaging.isFirst(),
        userCommentsWithPaging.getPageable().getPageNumber() + 1,
        userCommentsWithPaging.getPageable().getPageSize()
    );
  }

  public CatCommentInfoDto findComment(long contentId, long commentId) {
    CatComment comment = catCommentQuery.findComment(contentId, commentId);
    if (comment == null) {
      throw new PotendayException(ErrorCode.D003);
    }
    return CatCommentInfoDto.of(comment);
  }
}
