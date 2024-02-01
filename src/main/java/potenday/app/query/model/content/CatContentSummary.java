package potenday.app.query.model.content;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import potenday.app.domain.cat.CatContent;

@Getter
@Builder
public class CatContentSummary {

  private String catName;
  private String catAddress;
  private double catLat;
  private double catLon;
  private int catCommentCount;
  private int catFollowerCount;
  private LocalDateTime catCreatedAt;
  private LocalDateTime catUpdatedAt;
  private int catEmoji;

  public static CatContentSummary of(CatContent content) {
    return CatContentSummary.builder()
        .catName(content.getName())
        .catAddress(content.getJibunAddress().toString())
        .catLat(content.getCoordinate().getLat())
        .catLon(content.getCoordinate().getLon())
        .catCreatedAt(content.getCreatedAt())
        .catUpdatedAt(content.getUpdatedAt())
        .catEmoji(content.getCatEmoji())
        .build();
  }
}
