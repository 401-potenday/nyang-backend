package potenday.app.api.cat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import potenday.app.domain.cat.AddCatContent;
import potenday.app.domain.cat.status.CatPersonality;
import potenday.app.domain.image.AddCatContentImages;

@AllArgsConstructor
@NoArgsConstructor
public class AddCatContentRequest {

  @NotNull(message = "C010")
  @Size(min = 2, max = 10, message = "C010")
  private String name;

  @NotNull(message = "C012")
  @Length(max = 300, message = "C016")
  private String description;

  @NotNull(message = "C011")
  private String hasFriends;

  @NotNull(message = "C013")
  private List<CatPersonality> catPersonality;

  @NotNull
  private double lat;

  @NotNull
  private double lon;

  @NotNull(message = "C014")
  private String jibunAddrName;

  private String jibunMainAddrNo;

  private String jibunSido;

  private String jibunSigungu;

  private String jibunDong;

  private String jibunSubAddrNo;

  @NotEmpty
  @Valid
  private List<String> images;

  @NotNull(message = "C015")
  private String neuter;

  public AddCatContent toAddCatContent() {
    return AddCatContent.builder()
        .jibunSigungu(jibunSigungu)
        .jibunSido(jibunSido)
        .jibunDong(jibunDong)
        .jibunSubAddrNo(jibunSubAddrNo)
        .jibunMainAddrNo(jibunMainAddrNo)
        .lat(lat)
        .lon(lon)
        .hasFriends(hasFriends)
        .neuter(neuter)
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
