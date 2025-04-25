package de.schulung.quarkus;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;

@ApplicationScoped
public class CustomersInitializer {

  @Inject
  CustomersService customersService;

  @Startup
  public void init() {
    if (customersService.count() == 0) {
      Customer customer = new Customer();
      customer.setName("Tom Mayer");
      customer.setBirthdate(LocalDate.now().minusYears(30));
      customer.setState("active");
      customersService.create(customer);
    }
  }

}
