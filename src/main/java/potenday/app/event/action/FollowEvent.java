package potenday.app.event.action;

public record FollowEvent(long contentId, long userId) {

  public static FollowEvent of(long contentId, Long userId) {
    return new FollowEvent(contentId, userId);
  }
}
