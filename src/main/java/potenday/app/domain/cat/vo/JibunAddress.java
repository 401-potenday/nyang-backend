package potenday.app.domain.cat.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JibunAddress {

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

  @Builder
  public JibunAddress(final String jibunAddrName, final String jibunMainAddrNo,
      final String jibunSido, final String jibunSigungu, final String jibunDong, final String jibunSubAddrNo) {
    validateNotNull(jibunAddrName);
    this.jibunAddrName = jibunAddrName;
    this.jibunMainAddrNo = jibunMainAddrNo;
    this.jibunSido = jibunSido;
    this.jibunSigungu = jibunSigungu;
    this.jibunDong = jibunDong;
    this.jibunSubAddrNo = jibunSubAddrNo;
  }

  // 완전한 길이의 주소 정도는 validation 체크
  private void validateNotNull(String jibunAddrName) {
    if (jibunAddrName == null || jibunAddrName.isBlank()) {
      throw new IllegalArgumentException("CA01");
    }
  }

  public String toString() {
    return jibunAddrName;
  }
}
