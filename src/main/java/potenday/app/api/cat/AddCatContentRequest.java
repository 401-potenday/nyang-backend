package potenday.app.api.cat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import potenday.app.domain.cat.AddCatContent;
import potenday.app.domain.cat.status.CatFriends;
import potenday.app.domain.cat.status.CatNeuter;
import potenday.app.domain.cat.status.CatPersonality;
import potenday.app.domain.image.AddCatContentImages;

@AllArgsConstructor
@NoArgsConstructor
public class AddCatContentRequest {

  @NotNull(message = "C010")
  @Length(min = 2, max = 10, message = "C011")
  private String name;

  @Length(max = 300, message = "C016")
  private String description;

  @NotNull(message = "C002")
  private CatFriends hasFriends;

  @NotNull(message = "C013")
  private List<CatPersonality> catPersonality;

  @NotNull(message = "CG02")
  @Pattern(regexp = "^-?\\d+\\.\\d{5,}$", message = "CG03")
  private String lat;

  @NotNull(message = "CG03")
  @Pattern(regexp = "^-?\\d+\\.\\d{5,}$", message = "CG03")
  private String lon;

  @NotNull(message = "CA01")
  private String jibunAddrName;

  private String jibunMainAddrNo;

  private String jibunSido;

  private String jibunSigungu;

  private String jibunDong;

  private String jibunSubAddrNo;

  @NotEmpty(message = "CI02")
  private List<String> images;

  @NotNull(message = "C003")
  private CatNeuter neuter;

  public AddCatContent toAddCatContent() {
    return AddCatContent.builder()
        .jibunSigungu(jibunSigungu)
        .jibunSido(jibunSido)
        .jibunDong(jibunDong)
        .jibunSubAddrNo(jibunSubAddrNo)
        .jibunMainAddrNo(jibunMainAddrNo)
        .lat(Double.parseDouble(lat))
        .lon(Double.parseDouble(lon))
        .hasFriends(hasFriends.name())
        .neuter(neuter.name())
        .jibunAddrName(jibunAddrName)
        .name(name)
        .description(description)
        .catPersonality(catPersonality)
        .build();
  }

  public AddCatContentImages toAddCatImages() {
    return new AddCatContentImages(images);
  }
}