package potenday.app.api.content;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import potenday.app.domain.cat.content.AddCatContent;
import potenday.app.domain.cat.content.AddCatContentImages;
import potenday.app.domain.cat.status.CatFriends;
import potenday.app.domain.cat.status.CatNeuter;
import potenday.app.domain.cat.status.CatPersonality;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddCatContentRequest {

  @NotNull(message = "C010")
  @Length(min = 2, max = 10, message = "C011")
  private String name;

  @Length(max = 300, message = "C016")
  private String description;

  @NotNull(message = "C002")
  private CatFriends group;

  @NotNull(message = "C013")
  private List<CatPersonality> catPersonalities;

  @NotNull(message = "CG05")
  @Pattern(regexp = "^-?\\d+\\.\\d{5,}$", message = "CG03")
  private String lat;

  @NotNull(message = "CG06")
  @Pattern(regexp = "^-?\\d+\\.\\d{5,}$", message = "CG04")
  private String lng;

  @NotNull(message = "CA01")
  private String jibunAddrName;

  private String jibunMainAddrNo;

  private String jibunSido;

  private String jibunSigungu;

  private String jibunDong;

  private String jibunSubAddrNo;

  @NotEmpty(message = "CI02")
  private List<String> imageKeys;

  @NotNull(message = "C003")
  private CatNeuter neuter;

  @Min(value = 1, message = "C012")
  @Max(value = 18, message = "C012")
  private int catEmoji;

  public AddCatContent toAddCatContent() {
    return AddCatContent.builder()
        .catEmoji(catEmoji)
        .jibunSigungu(jibunSigungu)
        .jibunSido(jibunSido)
        .jibunDong(jibunDong)
        .jibunSubAddrNo(jibunSubAddrNo)
        .jibunMainAddrNo(jibunMainAddrNo)
        .lat(Double.parseDouble(lat))
        .lon(Double.parseDouble(lng))
        .hasFriends(group.name())
        .neuter(neuter.name())
        .jibunAddrName(jibunAddrName)
        .name(name)
        .description(description)
        .catPersonality(catPersonalities)
        .build();
  }

  public AddCatContentImages toAddCatImages() {
    return new AddCatContentImages(imageKeys);
  }
}
