package potenday.app.query.repository;

import lombok.Builder;

@Builder
public record CoordinationCondition(Double range, Double centerLat, Double centerLon) {

}
