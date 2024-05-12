package potenday.app.api.content;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import potenday.app.domain.cat.content.UpdateCatContent;
import potenday.app.domain.cat.status.CatFriends;
import potenday.app.domain.cat.status.CatNeuter;
import potenday.app.domain.cat.status.CatPersonality;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateCatContentRequest {

  @NotNull(message = "C010")
  @Length(min = 2, max = 10, message = "C011")
  private String name;

  @Length(max = 300, message = "C016")
  private String description;

  @NotNull(message = "C002")
  private CatFriends group;

  @NotNull(message = "C013")
  @NotEmpty(message = "C013")
  private List<CatPersonality> catPersonalities;

  @NotNull(message = "C003")
  private CatNeuter neuter;

  @Min(value = 1, message = "C012")
  @Max(value = 18, message = "C012")
  private int catEmoji;

  @NotEmpty(message = "CI02")
  private List<String> imageKeys;

  public UpdateCatContent toUpdateCatContent() {
    return UpdateCatContent.builder()
        .catEmoji(catEmoji)
        .hasFriends(group.name())
        .neuter(neuter.name())
        .description(description)
        .catPersonality(catPersonalities)
        .build();
  }

  public UpdateCatContentImages toUpdateCatImages() {
    return new UpdateCatContentImages(imageKeys);
  }
}
