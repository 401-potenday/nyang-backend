package potenday.app.query.model.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import potenday.app.api.content.CatContentEngagementSummary;
import potenday.app.domain.cat.content.CatContent;

@Getter
@Builder
public class CatContentSummary {

  @JsonProperty("contentId")
  private long catContentId;

  @JsonProperty("name")
  private String catName;

  @JsonProperty("jibunAddrName")
  private String catAddress;

  @JsonProperty("lat")
  private double catLat;

  @JsonProperty("lng")
  private double catLon;

  @JsonProperty("countOfComments")
  private long countOfComments;

  @JsonProperty("countOfFollowed")
  private long countOfFollowed;

  @JsonProperty("isFollowed")
  private boolean follow;

  @JsonProperty("createdAt")
  private LocalDateTime catCreatedAt;

  @JsonProperty("updatedAt")
  private LocalDateTime catUpdatedAt;

  @JsonProperty("catEmoji")
  private int catEmoji;

  public static CatContentSummary of(
      CatContent content,
      CatContentEngagementSummary catContentEngagementSummary,
      boolean isFollowed
  ) {
    return CatContentSummary.builder()
        .catContentId(content.getId())
        .catName(content.getName())
        .catAddress(content.getJibunAddress().toString())
        .catLat(content.getCoordinate().getLat())
        .catLon(content.getCoordinate().getLon())
        .catCreatedAt(content.getCreatedAt())
        .catUpdatedAt(content.getUpdatedAt())
        .catEmoji(content.getCatEmoji())
        .countOfFollowed(catContentEngagementSummary.countOfFollowed())
        .countOfComments(catContentEngagementSummary.countOfComments())
        .follow(isFollowed)
        .build();
  }

}
