package de.schulung.quarkus.domain;

import de.schulung.quarkus.shared.interceptors.FireEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class CustomersService {

  @Inject
  CustomersSink sink;

  public Stream<Customer> findAll() {
    return sink.findAll();
  }

  public long count() {
    return sink.count();
  }

  public Optional<Customer> findById(UUID uuid) {
    return sink.findById(uuid);
  }

  @FireEvent(CustomerCreatedEvent.class)
  public void create(@Valid Customer customer) {
    sink.create(customer);
  }

}
