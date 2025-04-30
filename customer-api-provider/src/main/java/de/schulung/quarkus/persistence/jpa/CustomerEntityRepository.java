package de.schulung.quarkus.persistence.jpa;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class CustomerEntityRepository
  implements PanacheRepositoryBase<CustomerEntity, UUID> {

//  public List<CustomerEntity> findAllByState(CustomerState state) {
//    return list("state", state);
//  }

}
