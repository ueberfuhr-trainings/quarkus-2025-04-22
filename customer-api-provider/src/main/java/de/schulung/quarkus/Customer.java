package de.schulung.quarkus;

import jakarta.json.bind.annotation.JsonbTransient;

import java.time.LocalDate;
import java.util.UUID;

public class Customer {

  private UUID uuid;
  private String name;
  private LocalDate birthdate;
  private String state;

  public UUID getUuid() {
    return uuid;
  }

  @JsonbTransient
  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
}
