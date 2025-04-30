package de.schulung.quarkus.persistence.inmemory;

import de.schulung.quarkus.domain.Customer;
import de.schulung.quarkus.domain.CustomersSink;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@DefaultBean
@Typed(CustomersSink.class)
public class InMemoryCustomersSink
  implements CustomersSink {

  private final Map<UUID, Customer> customers = new HashMap<>();

  @Override
  public Stream<Customer> findAll() {
    return this
      .customers
      .values()
      .stream();
  }

  @Override
  public long count() {
    return this
      .customers
      .size();
  }

  @Override
  public Optional<Customer> findById(UUID uuid) {
    return Optional
      .ofNullable(
        this
          .customers
          .get(uuid)
      );
  }

  @Override
  public void create(Customer customer) {
    customer.setUuid(UUID.randomUUID());
    this.customers.put(customer.getUuid(), customer);
  }
}
