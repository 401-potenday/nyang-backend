package potenday.app.domain.cat.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GeoLocation {

  private final static int MIN_DECIMAL_PLACES = 5;
  private static final double MIN_LATITUDE = -90.0;
  private static final double MAX_LATITUDE = 90.0;
  private static final double MIN_LONGITUDE = -180.0;
  private static final double MAX_LONGITUDE = 180.0;

  @Column(name = "lat", nullable = false)
  private double lat;

  @Column(name = "lon", nullable = false)
  private double lon;

  @Builder
  public GeoLocation(final double lat, final double lon) {
    validateGeolocationRange(lat, lon);
    this.lat = lat;
    this.lon = lon;
  }

  private void validateGeolocationRange(double lat, double lon) {
    checkGeolocationValidRange(lat, lon);
    checkCoordinatePrecision(lat, lon);
  }

  private void checkGeolocationValidRange(double lat, double lon) {
    if (lat < MIN_LATITUDE || lat > MAX_LATITUDE) {
      throw new IllegalArgumentException("CG01");
    }

    if (lon < MIN_LONGITUDE || lon > MAX_LONGITUDE) {
      throw new IllegalArgumentException("CG02");
    }
  }

  private void checkCoordinatePrecision(double lat, double lon) {
    // MIN_DECIMAL_PLACES 만큼 소수점이 최소 있어야 한다.
    String latLonPattern = String.format("^-?\\d+\\.\\d{%d,}$", MIN_DECIMAL_PLACES);

    if (!Double.toString(lat).matches(latLonPattern)) {
      throw new IllegalArgumentException("CG03");
    }

    if (!Double.toString(lon).matches(latLonPattern)) {
      throw new IllegalArgumentException("CG04");
    }
  }

}
