package potenday.app.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class FollowerEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public FollowerEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publishEvent(Object event) {
    applicationEventPublisher.publishEvent(event);
  }
}
