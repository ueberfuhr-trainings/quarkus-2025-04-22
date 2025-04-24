package de.schulung.quarkus;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class CustomersService {

  private final Map<UUID, Customer> customers = new HashMap<>();

  // TODO: separate
  {
    Customer customer = new Customer();
    customer.setUuid(UUID.randomUUID());
    customer.setName("Tom Mayer");
    customer.setBirthdate(LocalDate.now().minusYears(30));
    customer.setState("active");
    customers.put(customer.getUuid(), customer);
  }

  public Stream<Customer> findAll() {
    return this
      .customers
      .values()
      .stream();
  }

  public Optional<Customer> findById(UUID uuid) {
    return Optional
      .ofNullable(
        this
          .customers
          .get(uuid)
      );
  }

  public void create(Customer customer) {
    customer.setUuid(UUID.randomUUID());
    this.customers.put(customer.getUuid(), customer);
  }

}
