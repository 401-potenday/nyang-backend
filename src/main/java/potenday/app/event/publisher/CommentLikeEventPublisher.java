package potenday.app.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import potenday.app.event.action.CommentLikeEvent;
import potenday.app.event.action.CommentUnlikeEvent;

@Component
public class CommentLikeEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public CommentLikeEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publishEvent(CommentLikeEvent commentLikeEvent) {
    applicationEventPublisher.publishEvent(commentLikeEvent);
  }

  public void publishUnlikeEvent(CommentUnlikeEvent commentUnlikeEvent) {
    applicationEventPublisher.publishEvent(commentUnlikeEvent);
  }
}
