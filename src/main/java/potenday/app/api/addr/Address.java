package potenday.app.api.addr;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "SEARCH_ADDR")
public class Address{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "sido_code", nullable = false)
  private Integer sidoCode;

  @Column(name = "sido_name", nullable = false, length = 100)
  private String sidoName;

  @Column(name = "gu_code", nullable = false, length = 100)
  private String guCode;

  @Column(name = "sigungu_name", nullable = false)
  private String sigunguName;

  @Column(name = "dong_code", nullable = false)
  private Integer  dongCode;

  @Column(name = "dong_name", nullable = false, length = 100)
  private String dongName;

  @Column(name = "full_addr", nullable = false)
  private String fullAddr;
}
