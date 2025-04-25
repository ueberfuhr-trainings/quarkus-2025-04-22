package de.schulung.quarkus.domain;

public record CustomerCreatedEvent(
  Customer customer
) {
}
