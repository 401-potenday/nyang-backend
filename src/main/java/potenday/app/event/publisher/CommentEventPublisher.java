package potenday.app.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import potenday.app.event.action.CommentAddEvent;

@Component
public class CommentEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public CommentEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publishEvent(CommentAddEvent commentAddEvent) {
    applicationEventPublisher.publishEvent(commentAddEvent);
  }
}
