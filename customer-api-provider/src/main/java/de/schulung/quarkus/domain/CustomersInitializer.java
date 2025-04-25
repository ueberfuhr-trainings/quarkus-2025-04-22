package de.schulung.quarkus.domain;

import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.Startup;
import jakarta.inject.Inject;

import java.time.LocalDate;

@IfBuildProperty(
  name = "customers.initialization.enabled",
  stringValue = "true"
)
// @Dependent
public class CustomersInitializer {

  @Inject
  CustomersService customersService;

  @Startup
  public void init() {
    if (customersService.count() == 0) {
      customersService.create(
        new Customer()
          .setName("Tom Mayer")
          .setBirthdate(LocalDate.now().minusYears(30))
      );
    }
  }

}
