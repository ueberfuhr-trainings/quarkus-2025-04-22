package de.schulung.quarkus.persistence.jpa;

import de.schulung.quarkus.domain.Customer;
import de.schulung.quarkus.domain.CustomersSink;
import io.quarkus.arc.properties.UnlessBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@Typed(CustomersSink.class)
@UnlessBuildProperty(
  name = "customers.jpa.enabled",
  stringValue = "false",
  enableIfMissing = true
)
public class JpaCustomersSink
  implements CustomersSink {

  @Inject
  CustomerEntityRepository repository;
  @Inject
  CustomerEntityMapper mapper;

  @Override
  public Stream<Customer> findAll() {
    return this
      .repository
      .findAll()
      .stream()
      .map(this.mapper::map);
  }

  @Override
  public long count() {
    return this
      .repository
      .count();
  }

  @Override
  public Optional<Customer> findById(UUID uuid) {
    return this
      .repository
      .findByIdOptional(uuid)
      .map(this.mapper::map);
  }

  @Transactional
  @Override
  public void create(Customer customer) {
    var entity = this.mapper.map(customer);
    this.repository.persist(entity);
    //customer.setUuid(entity.getUuid());
    this.mapper.copy(entity, customer);
  }
}
