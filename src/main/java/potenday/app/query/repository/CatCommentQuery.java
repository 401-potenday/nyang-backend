package potenday.app.query.repository;

import static potenday.app.domain.cat.comment.QCatComment.catComment;
import static potenday.app.domain.cat.comment.QCatCommentImage.catCommentImage;
import static potenday.app.domain.cat.commentlikes.QCatCommentLike.catCommentLike;
import static potenday.app.domain.cat.content.QCatContent.catContent;
import static potenday.app.domain.cat.follow.QCatFollow.catFollow;
import static potenday.app.domain.user.QUser.user;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import potenday.app.domain.cat.comment.CatComment;
import potenday.app.domain.cat.comment.CatCommentImage;
import potenday.app.domain.user.UserActivateStatus;
import potenday.app.query.model.comment.CatCommentImageWithOrder;
import potenday.app.query.model.comment.CatCommentWithUserNicknameAndImages;

@Component
public class CatCommentQuery {

  private final JPAQueryFactory queryFactory;

  public CatCommentQuery(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  public PageImpl<CatCommentWithUserNicknameAndImages> findAllCommentWithPaging(long contentId,
      Pageable pageable) {

    List<Tuple> results = getFetchComments(contentId, pageable);

    Map<Long, CatCommentWithUserNicknameAndImages> commentMap = new HashMap<>();
    results.forEach(tuple -> {
      CatComment commentEntity = tuple.get(catComment);
      String nickname = tuple.get(user.nickname);
      CatCommentImage commentImageEntity = tuple.get(catCommentImage);
      CatCommentWithUserNicknameAndImages comment = commentMap.computeIfAbsent(commentEntity.getId(), id -> new CatCommentWithUserNicknameAndImages(
          commentEntity.getId(),
          new ArrayList<>(),
          commentEntity.getComment(),
          commentEntity.getCreatedAt(),
          commentEntity.getUpdatedAt(),
          nickname
      ));
      if (hasImageInComment(commentImageEntity)) {
        comment.catCommentImageWithOrders().add(
            new CatCommentImageWithOrder(
                commentImageEntity.getImageUri(),
                commentImageEntity.getImageOrder()
            )
        );
      }
    });


    // 전체 댓글 수를 조회하는 쿼리
    long total = countQuery(contentId);

    // PageImpl 객체를 생성하여 반환
    return new PageImpl<>(new ArrayList<>(commentMap.values()), pageable, total);
  }

  private static boolean hasImageInComment(CatCommentImage commentImageEntity) {
    return commentImageEntity != null;
  }

  private List<Tuple> getFetchComments(long contentId, Pageable pageable) {
    return queryFactory
        .select(catComment, user.nickname, catCommentImage)
        .from(catComment)
        .join(catContent).on(catContent.id.eq(catComment.catContentId))
        .join(user).on(catContent.userId.eq(user.id))
        .leftJoin(catCommentImage).on(catComment.id.eq(catCommentImage.catCommentId))
        .where(
            catComment.catContentId.eq(contentId),
            imageNotDeleted(),
            activeUser(),
            contentNotDeleted()
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(catComment.createdAt.desc())
        .fetch();
  }

  private Long countQuery(long contentId) {
    return queryFactory
        .select(catComment.count())
        .from(catComment)
        .join(catContent).on(catContent.id.eq(catComment.catContentId))
        .join(user).on(catContent.userId.eq(user.id))
        .leftJoin(catCommentImage).on(catComment.id.eq(catCommentImage.catCommentId))
        .where(
            catComment.catContentId.eq(contentId),
            imageNotDeleted(),
            activeUser(),
            contentNotDeleted()
        )
        .fetchOne();
  }

  private BooleanExpression imageNotDeleted() {
    return catCommentImage.isNull().or(catCommentImage.isDeleted.isFalse()); // 이미지가 삭제되지 않은 경우에만 조회합니다.
  }

  private BooleanExpression contentNotDeleted() {
    return catContent.isDeleted.isFalse();
  }

  private BooleanExpression activeUser() {
    return user.isWithDraw.isFalse().and(user.activateStatus.eq(UserActivateStatus.ACTIVATE));
  }

  public long computeFollowerCount(Long catContentId) {
    Long fetchResult = queryFactory
        .select(catFollow.id.count())
        .from(catFollow)
        .where(catFollow.catContentId.eq(catContentId))
        .fetchOne();
    return fetchResult != null ? fetchResult : 0;
  }

  public long computeCommentLikesCount(Long catCommentId) {
    Long fetchResult = queryFactory
        .select(catCommentLike.count())
        .from(catCommentLike)
        .where(eqCommentId(catCommentId))
        .fetchOne();
    return fetchResult != null ? fetchResult : 0;
  }

  private static BooleanExpression eqCommentId(Long catCommentId) {
    return catCommentLike.catCommentLikeId.catCommentId.eq(catCommentId);
  }

  public long countContentComments(Long catContentId) {
    Long fetchResult = queryFactory
        .select(catComment.count())
        .from(catComment)
        .where(
            catComment.isDeleted.isFalse(),
            catComment.catContentId.eq(catContentId)
        )
        .fetchOne();
    return fetchResult != null ? fetchResult : 0;
  }
}
