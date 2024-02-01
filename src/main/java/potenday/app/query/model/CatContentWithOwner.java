package potenday.app.query.model;

import java.util.List;
import java.util.Set;
import potenday.app.domain.cat.status.CatFriends;
import potenday.app.domain.cat.status.CatNeuter;
import potenday.app.domain.cat.status.CatPersonality;

public class CatContentWithOwner {

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


//  private boolean isBookMark;
//  private int countOfBookMark;
//  private int numberOfComments;
//  private int numberOfCatSlaves;

  private Long userUid;
  private String nickname;
}
