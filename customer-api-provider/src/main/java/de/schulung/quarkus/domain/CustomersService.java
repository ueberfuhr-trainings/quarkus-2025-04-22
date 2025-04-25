package de.schulung.quarkus.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class CustomersService {

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
  }

}
