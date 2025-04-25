package de.schulung.quarkus.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class CustomersService {

  @Inject
  Event<Object> eventPublisher;

  private final Map<UUID, Customer> customers = new HashMap<>();

  public Stream<Customer> findAll() {
    return this
      .customers
      .values()
      .stream();
  }

  public long count() {
    return this
      .customers
      .size();
  }

  public Optional<Customer> findById(UUID uuid) {
    return Optional
      .ofNullable(
        this
          .customers
          .get(uuid)
      );
  }

  public void create(@Valid Customer customer) {
    customer.setUuid(UUID.randomUUID());
    this.customers.put(customer.getUuid(), customer);
    eventPublisher
      .select(CustomerCreatedEvent.class)
      .fire(new CustomerCreatedEvent(customer));
  }

}
