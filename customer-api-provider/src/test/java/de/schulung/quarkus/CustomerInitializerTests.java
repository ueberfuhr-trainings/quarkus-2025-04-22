package de.schulung.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class CustomerInitializerTests {

  @Inject
  CustomersService customersService;

  @Test
  void whenInit_thenCustomersNotEmpty() {
    assertThat(customersService.count())
      .isGreaterThan(0L);
  }

}
