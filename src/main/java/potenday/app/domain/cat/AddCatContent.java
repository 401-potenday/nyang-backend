package potenday.app.domain.cat;

import java.util.List;
import lombok.Builder;
import potenday.app.domain.cat.status.CatFriends;
import potenday.app.domain.cat.status.CatNeuter;
import potenday.app.domain.cat.status.CatPersonalities;
import potenday.app.domain.cat.vo.Coordinate;
import potenday.app.domain.cat.vo.JibunAddress;

@Builder
public class AddCatContent {

  private String name;

  private String hasFriends;

  private String description;

  private List<String> catPersonality;

  private double lat;

  private double lon;

  private String jibunAddrName;

  private String jibunMainAddrNo;

  private String jibunSido;

  private String jibunSigungu;

  private String jibunDong;

  private String jibunSubAddrNo;

  private String neuter;

  public CatContent toContent() {
    return CatContent.builder()
        .name(name)
        .hasFriends(CatFriends.from(hasFriends))
        .neuter(CatNeuter.from(hasFriends))
        .jibunAddress(JibunAddress.builder()
            .jibunAddrName(jibunAddrName)
            .jibunDong(jibunDong)
            .jibunMainAddrNo(jibunMainAddrNo)
            .jibunSido(jibunSido)
            .jibunSigungu(jibunSigungu)
            .jibunSubAddrNo(jibunSubAddrNo)
            .build())
        .catPersonalities(CatPersonalities.of(catPersonality))
        .coordinate(Coordinate.builder()
            .lat(lat)
            .lon(lon)
            .build())
        .description(description)
        .build();
  }
}
