package potenday.app.api.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import potenday.app.domain.cat.status.CatFriends;
import potenday.app.domain.cat.status.CatNeuter;
import potenday.app.domain.cat.status.CatPersonality;
import potenday.app.query.model.content.CatContentDetails;

@Getter
@Builder
public class CatContentResponse {

  @JsonProperty(value = "contentId")
  private long contentId;

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "description")
  private String description;

  @JsonProperty(value = "group")
  private CatFriends group;

  @JsonProperty(value = "catPersonalities")
  private Set<CatPersonality> catPersonalities;

  @JsonProperty(value = "lat")
  private String lat;

  @JsonProperty(value = "lng")
  private String lng;

  @JsonProperty(value = "jibunAddrName")
  private String jibunAddrName;

  @JsonProperty(value = "jibunMainAddrNo")
  private String jibunMainAddrNo;

  @JsonProperty(value = "jibunSido")
  private String jibunSido;

  @JsonProperty(value = "jibunSigungu")
  private String jibunSigungu;

  @JsonProperty(value = "jibunDong")
  private String jibunDong;

  @JsonProperty(value = "jibunSubAddrNo")
  private String jibunSubAddrNo;

  @JsonProperty(value = "images")
  private List<String> images;

  @JsonProperty(value = "neuter")
  private CatNeuter neuter;

  @JsonProperty(value = "createdAt")
  private String createdAt;

  @JsonProperty(value = "updatedAt")
  private String updatedAt;

  @JsonProperty(value = "catEmoji")
  private int catEmoji;

  @JsonProperty(value = "isBookMark")
  private boolean isBookMark;

  @JsonProperty(value = "countOfBookMark")
  private int countOfBookMark;

  @JsonProperty(value = "numberOfComments")
  private int numberOfComments;

  @JsonProperty(value = "numberOfCatSlaves")
  private int numberOfCatSlaves;

  @JsonProperty(value = "userUid")
  private long userUid;

  @JsonProperty(value = "nickname")
  private String nickname;

  public static CatContentResponse from(CatContentDetails catContentDetails) {
    return CatContentResponse.builder()
        .contentId(catContentDetails.getContentId())
        .catEmoji(catContentDetails.getCatEmoji())
        .countOfBookMark(catContentDetails.getCountOfBookMark())
        .description(catContentDetails.getDescription())
        .group(catContentDetails.getGroup())
        .catPersonalities(catContentDetails.getCatPersonalities())
        .lat(catContentDetails.getLat())
        .lng(catContentDetails.getLon())
        .images(catContentDetails.getImages())
        .isBookMark(catContentDetails.isBookMark())
        .jibunAddrName(catContentDetails.getJibunAddrName())
        .jibunDong(catContentDetails.getJibunDong())
        .jibunMainAddrNo(catContentDetails.getJibunMainAddrNo())
        .jibunSido(catContentDetails.getJibunSido())
        .jibunSigungu(catContentDetails.getJibunSigungu())
        .jibunSubAddrNo(catContentDetails.getJibunSubAddrNo())
        .name(catContentDetails.getName())
        .neuter(catContentDetails.getNeuter())
        .numberOfCatSlaves(catContentDetails.getNumberOfCatSlaves())
        .numberOfComments(catContentDetails.getNumberOfComments())
        .userUid(catContentDetails.getUserUid())
        .nickname(catContentDetails.getNickname())
        .updatedAt(catContentDetails.getUpdatedAt())
        .createdAt(catContentDetails.getCreatedAt())
        .build();
  }
}
