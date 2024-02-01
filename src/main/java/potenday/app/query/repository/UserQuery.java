package potenday.app.query.repository;

import static potenday.app.domain.user.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;
import potenday.app.query.model.user.UserNickname;

@Component
public class UserQuery {

  private final JPAQueryFactory queryFactory;

  public UserQuery(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  public UserNickname fetchUserSimpleInfo(long userId) {
    return queryFactory.select(Projections.constructor(
            UserNickname.class,
            user.id,
            user.nickname
        ))
        .from(user)
        .where(user.id.eq(userId))
        .fetchOne();
  }
}
