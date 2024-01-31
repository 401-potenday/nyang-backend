package potenday.app.domain.cat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potenday.app.domain.BaseTimeEntity;

@Getter
@Entity
@Table(name = "CAT_CONTENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatContent extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "has_friends", nullable = false, columnDefinition = "CHAR(10)")
  private String hasFriends;

  @Column(name = "description", nullable = false, columnDefinition = "VARCHAR(255)")
  private String description;

  @Column(name = "personality",nullable = false, columnDefinition = "CHAR(20)")
  private String personality;

  @Column(name = "lat", nullable = false)
  private double lat;

  @Column(name = "lon", nullable = false)
  private double lon;

  @Column(name = "road_address")
  private String roadAddress;

  @Column(name = "jibun_addr_name")
  private String jibunAddrName;

  @Column(name = "jibun_main_addr_no")
  private String jibunMainAddrNo;

  @Column(name = "jibun_sido")
  private String jibunSido;

  @Column(name = "jibun_sigungu")
  private String jibunSigungu;

  @Column(name = "jibun_dong")
  private String jibunDong;

  @Column(name = "jibun_sub_addr_no")
  private String jibunSubAddrNo;

  @Column(name = "neuter", nullable = false, columnDefinition = "CHAR(10)")
  private String neuter;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT")
  private boolean isDeleted;

  @Builder
  public CatContent(final Long id, final String name, final String hasFriends,
      final String description,
      final String personality, final double lat, final double lon,
      final String roadAddress,
      final String jibunAddrName, final String jibunMainAddrNo, final String jibunSido,
      final String jibunSigungu,
      final String jibunDong, final String jibunSubAddrNo, final String neuter) {
    this.id = id;
    this.name = name;
    this.hasFriends = hasFriends;
    this.description = description;
    this.personality = personality;
    this.lat = lat;
    this.lon = lon;
    this.roadAddress = roadAddress;
    this.jibunAddrName = jibunAddrName;
    this.jibunMainAddrNo = jibunMainAddrNo;
    this.jibunSido = jibunSido;
    this.jibunSigungu = jibunSigungu;
    this.jibunDong = jibunDong;
    this.jibunSubAddrNo = jibunSubAddrNo;
    this.neuter = neuter;
  }
}