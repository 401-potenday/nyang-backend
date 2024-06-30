package potenday.app.query.repository;

import static potenday.app.domain.cat.content.QCatContent.catContent;
import static potenday.app.domain.cat.content.QCatContentImage.catContentImage;
import static potenday.app.domain.cat.follow.QCatFollow.catFollow;
import static potenday.app.domain.report.QCatContentReport.catContentReport;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import potenday.app.api.content.search.ContentSearchCondition;
import potenday.app.api.content.search.DistanceOrder;
import potenday.app.domain.auth.AppUser;
import potenday.app.domain.cat.content.CatContent;
import potenday.app.domain.cat.content.CatContentImage;
import potenday.app.domain.report.CatContentReportStatus;
import potenday.app.domain.report.QCatContentReport;

@Component
public class CatContentQuery {

  private final JPAQueryFactory queryFactory;

  public CatContentQuery(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  public boolean isFollowed(long userId, long contentId) {
    return queryFactory
        .selectOne()
        .from(catFollow)
        .where(
            catFollow.catContentId.eq(contentId),
            catFollow.userId.eq(userId)
        )
        .fetchFirst() != null;
  }

  public CatContent fetchContent(long contentId) {
    return queryFactory
        .selectFrom(catContent)
        .where(eqContentId(contentId), notContentDeleted())
        .fetchOne();
  }

  public List<CatContentImage> fetchContentImages(long contentId) {
    return queryFactory
        .selectFrom(catContentImage)
        .where(catContentImage.catContentId.eq(contentId))
        .fetch();
  }

  private BooleanExpression eqContentId(Long contentId) {
    return catContent.id.eq(contentId);
  }

  private BooleanExpression notContentDeleted() {
    return catContent.isDeleted.isFalse();
  }

  public Page<CatContent> fetchContentsBySearchCondition(AppUser appUser, ContentSearchCondition searchCondition, Pageable pageable) {

    var jpaQuery = queryFactory
        .selectFrom(catContent)
        .leftJoin(catFollow).on(catFollow.catContentId.eq(catContent.id))
        .leftJoin(catContentReport).on(catContentReport.contentId.eq(catContent.id))
        .where(
            onlyFollow(appUser, searchCondition.follow()),
            withInDistance(searchCondition.coordinationCondition()),
            notContentDeleted(),
            isNotReportedOrAllRejected()
        );

    // 거리순 정렬
    if (searchCondition.distanceOrder() != null) {
      jpaQuery.orderBy(orderByDistance(searchCondition));
    }

    long totalCount = jpaQuery.fetchCount();
    var contents = jpaQuery
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    return new PageImpl<>(contents, pageable, totalCount);
  }

  private OrderSpecifier<Double> orderByDistance(ContentSearchCondition searchCondition) {
    NumberExpression<Double> distance = catContent.coordinate.lat.subtract(
            searchCondition.coordinationCondition().centerLat())
        .multiply(
            catContent.coordinate.lat.subtract(searchCondition.coordinationCondition().centerLat()))
        .add(catContent.coordinate.lon.subtract(searchCondition.coordinationCondition().centerLon())
            .multiply(catContent.coordinate.lon.subtract(
                searchCondition.coordinationCondition().centerLon())))
        .sqrt();

    return searchCondition.distanceOrder() == DistanceOrder.ASC ? distance.asc() : distance.desc();
  }

  private BooleanExpression onlyFollow(AppUser appUser, Boolean follow) {
    if (appUser == null || follow == null || !follow) {
      return null;
    }
    return catFollow.userId.eq(appUser.id());
  }

  private BooleanExpression withInDistance(CoordinationCondition coordinationCondition) {
    if (coordinationCondition == null) {
      return null;
    }

    double deltaLat = coordinationCondition.range() / (111.0 * 1000); // 위도 변화량 (m 단위)
    double deltaLon = coordinationCondition.range() / (111.0 * 1000 * Math.cos(
        Math.toRadians(coordinationCondition.centerLat()))); // 경도 변화량 (m 단위)

    double north = coordinationCondition.centerLat() + deltaLat;
    double south = coordinationCondition.centerLat() - deltaLat;
    double east = coordinationCondition.centerLon() + deltaLon;
    double west = coordinationCondition.centerLon() - deltaLon;

    return (catContent.coordinate.lat.between(south, north)).and(
        catContent.coordinate.lon.between(west, east));
  }

  public boolean existsByContentId(long contentId) {
    return queryFactory
        .selectOne()
        .from(catContent)
        .where(
            catContent.id.eq(contentId),
            catContent.isDeleted.isFalse()
        )
        .fetchFirst() != null;
  }

  public long computeFollowerCount(Long catContentId) {
    Long fetchResult = queryFactory
        .select(catFollow.id.count())
        .from(catFollow)
        .where(catFollow.catContentId.eq(catContentId))
        .fetchOne();
    return fetchResult != null ? fetchResult : 0;
  }

  public Page<CatContent> fetchMyContentsByCondition(AppUser appUser, Pageable pageable) {
    var jpaQuery = queryFactory
        .selectFrom(catContent)
        .leftJoin(catFollow).on(catFollow.catContentId.eq(catContent.id))
        .leftJoin(catContentReport).on(catContentReport.contentId.eq(catContent.id))
        .where(
            eqOwnerId(appUser),
            notContentDeleted(),
            isNotReportedOrAllRejected()
        )
        .orderBy(catContent.createdAt.desc());

    long totalCount = jpaQuery.fetchCount();
    var contents = jpaQuery
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    return new PageImpl<>(contents, pageable, totalCount);
  }

  private Predicate eqOwnerId(AppUser appUser) {
    return catContent.userId.eq(appUser.id());
  }

  private BooleanExpression isNotReportedOrAllRejected() {
    // 서브쿼리 작성
    QCatContentReport subCatContentReport = new QCatContentReport("subCatContentReport");

    // 서브쿼리를 통해 모든 상태가 "REJECT"가 아닌 콘텐츠 ID를 가져옵니다.
    JPQLQuery<Long> subQuery = JPAExpressions.select(subCatContentReport.contentId)
        .from(subCatContentReport)
        .where(subCatContentReport.status.ne(CatContentReportStatus.REJECT))
        .groupBy(subCatContentReport.contentId);

    // 메인 쿼리에 서브쿼리를 사용하여 조건을 작성합니다.
    return catContent.id.notIn(subQuery);
  }
}
