package de.schulung.quarkus.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public class Customer {

  private UUID uuid;
  @NotNull
  @Size(min = 3, max = 100)
  private String name;
  @NotNull
  private LocalDate birthdate;
  private CustomerState state = CustomerState.ACTIVE;

  public UUID getUuid() {
    return uuid;
  }

  public Customer setUuid(UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  public String getName() {
    return name;
  }

  public Customer setName(String name) {
    this.name = name;
    return this;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public Customer setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
    return this;
  }

  public CustomerState getState() {
    return state;
  }

  public Customer setState(CustomerState state) {
    this.state = state;
    return this;
  }
}
