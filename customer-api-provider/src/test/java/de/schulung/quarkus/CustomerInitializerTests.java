package de.schulung.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class CustomerInitializerTests {

  public static class InitializationEnabledProfile
    implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
      return Map.of(
        "customers.initialization.enabled", "true"
      );
    }
  }

  public static class InitializationDisabledProfile
    implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
      return Map.of(
        "customers.initialization.enabled", "false"
      );
    }
  }

  @Inject
  CustomersService customersService;

  @Nested
  @TestProfile(InitializationEnabledProfile.class)
  class InitializationEnabledProfileTests {

    @Test
    void whenInit_thenCustomersNotEmpty() {
      assertThat(customersService.count())
        .isGreaterThan(0L);
    }

  }

  @Nested
  @TestProfile(InitializationDisabledProfile.class)
  class InitializationDisabledProfileTests {

    @Test
    void whenInit_thenCustomersEmpty() {
      assertThat(customersService.count())
        .isEqualTo(0L);
    }

  }

}
