package potenday.app.api.addr;

import static potenday.app.api.addr.QAddress.address;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AddressQuery {

  private final JPAQueryFactory queryFactory;

  public AddressQuery(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  public List<Address> findAll() {
    return queryFactory.selectFrom(address).fetch();
  }
}
