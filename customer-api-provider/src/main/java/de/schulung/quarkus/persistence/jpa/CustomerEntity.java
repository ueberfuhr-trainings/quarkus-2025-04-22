package de.schulung.quarkus.persistence.jpa;

import de.schulung.quarkus.domain.CustomerState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

// Name im JPQL: "SELECT c FROM Customer c"
@Entity(name = "Customer")
// Name der Tabelle in der Datenbank
@Table(name = "CUSTOMERS")
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID uuid;
  @NotNull
  @Size(min = 3, max = 100)
  private String name;
  @NotNull
  @Column(name = "BIRTH_DATE")
  private LocalDate birthdate;
  // TODO check?
  private CustomerState state = CustomerState.ACTIVE;

  public UUID getUuid() {
    return uuid;
  }

  public CustomerEntity setUuid(UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  public String getName() {
    return name;
  }

  public CustomerEntity setName(String name) {
    this.name = name;
    return this;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public CustomerEntity setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
    return this;
  }

  public CustomerState getState() {
    return state;
  }

  public CustomerEntity setState(CustomerState state) {
    this.state = state;
    return this;
  }
}
