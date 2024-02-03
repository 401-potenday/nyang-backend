package potenday.app.query.repository;

import static potenday.app.domain.cat.content.QCatContent.catContent;
import static potenday.app.domain.cat.content.QCatContentImage.catContentImage;
import static potenday.app.domain.cat.follow.QCatFollow.catFollow;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import potenday.app.api.content.search.ContentSearchCondition;
import potenday.app.api.content.search.DistanceOrder;
import potenday.app.domain.cat.content.CatContent;
import potenday.app.domain.cat.content.CatContentImage;

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
        .where(eqContentId(contentId), notDeleted())
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

  private BooleanExpression notDeleted() {
    return catContent.isDeleted.isFalse();
  }

  public Page<CatContent> fetchContentsBySearchCondition(ContentSearchCondition searchCondition,
      Pageable pageable) {
    var jpaQuery = queryFactory
        .selectFrom(catContent)
        .where(withInDistance(searchCondition.coordinationCondition()));

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
}
