package potenday.app.query.repository;

import static potenday.app.domain.cat.QCatContent.catContent;
import static potenday.app.domain.image.QCatContentImage.catContentImage;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.stereotype.Component;
import potenday.app.domain.cat.CatContent;
import potenday.app.domain.image.CatContentImage;

@Component
public class CatContentQuery {

  private final JPAQueryFactory queryFactory;

  public CatContentQuery(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
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
}
