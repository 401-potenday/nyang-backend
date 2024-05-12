package potenday.app.domain.cat.content;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import potenday.app.domain.cat.status.CatPersonality;

@Builder
@Getter
public class UpdateCatContent {

  private String hasFriends;

  private String description;

  private List<CatPersonality> catPersonality;

  private String neuter;

  private int catEmoji;
}
