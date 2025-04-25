package de.schulung.quarkus.infrastructure;

import de.schulung.quarkus.domain.CustomerCreatedEvent;
import io.quarkus.arc.log.LoggerName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;

@ApplicationScoped
public class CustomerEventLogger {

  @LoggerName("customer-events")
  Logger log;

  public void logCustomerEvent(
    @Observes
    CustomerCreatedEvent event
  ) {
    log.infov(
      "Customer created: {0}",
      event.customer().getUuid()
    );
  }

}
