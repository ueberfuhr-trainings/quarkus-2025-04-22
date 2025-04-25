package de.schulung.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
class CustomersServiceTests {

  @Inject
  CustomersService customersService;

  @Test
  void whenCreateInvalidCustomer_ThenException() {
    Customer customer = new Customer();
    customer.setBirthdate(LocalDate.now().minusYears(20));
    customer.setState("active");
    assertThatThrownBy(() -> customersService.create(customer))
      .isNotNull();
  }

}
