package potenday.app.event.action;

public record UnFollowEvent(long contentId, long userId) {

  public static UnFollowEvent of(long contentId, long userId) {
    return new UnFollowEvent(contentId, userId);
  }
}
