package de.schulung.quarkus.domain;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface CustomersSink {

  Stream<Customer> findAll();

  default long count() {
    return findAll()
      .count();
  }

  default Optional<Customer> findById(UUID uuid) {
    return findAll()
      .filter(customer -> customer.getUuid().equals(uuid))
      .findFirst();
  }

  void create(Customer customer);

}
