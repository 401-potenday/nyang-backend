package potenday.app.api.content.search;

import lombok.Builder;
import potenday.app.query.repository.CoordinationCondition;

@Builder
public record ContentSearchCondition(
    Boolean follow,
    DistanceOrder distanceOrder,
    CreateTimeOrder createTimeOrder,
    CoordinationCondition coordinationCondition
) {

  public static ContentSearchCondition of(
      Boolean follow,
      DistanceOrder distanceOrder,
      CreateTimeOrder createTimeOrder,
      CoordinationCondition coordinationCondition
  ) {
    return new ContentSearchCondition(follow, distanceOrder, createTimeOrder, coordinationCondition);
  }
}