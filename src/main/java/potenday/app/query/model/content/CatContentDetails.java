package potenday.app.query.model.content;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import potenday.app.api.content.CatContentEngagementSummary;
import potenday.app.domain.cat.content.CatContent;
import potenday.app.domain.cat.content.CatContentImage;
import potenday.app.domain.cat.status.CatFriends;
import potenday.app.domain.cat.status.CatNeuter;
import potenday.app.domain.cat.status.CatPersonality;
import potenday.app.query.model.user.UserNickname;

@Builder
@Getter
@ToString
public class CatContentDetails {

  private long contentId;
  private String name;
  private String description;
  private CatFriends group;
  private Set<CatPersonality> catPersonalities;
  private String lat;
  private String lon;
  private String jibunAddrName;
  private String jibunMainAddrNo;
  private String jibunSido;
  private String jibunSigungu;
  private String jibunDong;
  private String jibunSubAddrNo;
  private List<String> images;
  private CatNeuter neuter;
  private String createdAt;
  private String updatedAt;
  private int catEmoji;

  // 다른 곳에서!
  private boolean isFollowed;
  private long countOfFollowed;
  private long countOfComments;

  private long userUid;
  private String nickname;

  private boolean isArchived;
  private boolean isAuthor;

  public static CatContentDetails of(
      CatContent content,
      List<CatContentImage> catContentImages,
      UserNickname userNickname,
      CatContentEngagementSummary contentEngagementSummary,
      boolean isArchived,
      boolean isAuthor,
      boolean isFollowed
  ) {
    CatContentDetailsBuilder catContentDetailsBuilder = CatContentDetails.builder()
        .contentId(content.getId())
        .name(content.getName())
        .description(content.getDescription())
        .group(content.getHasFriends())
        .catPersonalities(content.getCatPersonalities().getPersonalities())
        .lat(String.valueOf(content.getCoordinate().getLat()))
        .lon(String.valueOf(content.getCoordinate().getLon()))
        .jibunMainAddrNo(content.getJibunAddress().getJibunMainAddrNo())
        .jibunAddrName(content.getJibunAddress().getJibunAddrName())
        .jibunSido(content.getJibunAddress().getJibunSido())
        .jibunSigungu(content.getJibunAddress().getJibunSigungu())
        .jibunDong(content.getJibunAddress().getJibunDong())
        .jibunSubAddrNo(content.getJibunAddress().getJibunSubAddrNo())
        .catEmoji(content.getCatEmoji())
        .images(catContentImages.stream()
            .sorted(Comparator.comparingInt(CatContentImage::getImageOrder))
            .map(CatContentImage::getImageUri)
            .collect(Collectors.toList()))
        .neuter(content.getNeuter())

        .createdAt(content.getCreatedAt().toString())
        .updatedAt(content.getUpdatedAt().toString())

        // content 통계
        .countOfFollowed(contentEngagementSummary.countOfFollowed())
        .countOfComments(contentEngagementSummary.countOfComments())
        .isFollowed(isFollowed)
        .isAuthor(isAuthor)
        .isArchived(isArchived)
        .userUid(userNickname.getUserId())
        .countOfComments(contentEngagementSummary.countOfComments())
        .nickname(userNickname.getNickname());

    if (isArchived) {
      catContentDetailsBuilder
          .isAuthor(false)
          .userUid(0)
          .nickname(null);
    }

    return catContentDetailsBuilder.build();
  }
}
