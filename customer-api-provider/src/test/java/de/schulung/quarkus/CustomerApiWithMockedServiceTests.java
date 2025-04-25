package de.schulung.quarkus;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@QuarkusTest
class CustomerApiWithMockedServiceTests {

  @InjectMock
  CustomersService customersService;

  @Test
  void whenGetCustomers_thenOk() {
    var customer = new Customer();
    customer.setUuid(UUID.randomUUID());
    customer.setName("Tom Mayer");
    customer.setBirthdate(LocalDate.of(2001, Month.APRIL, 23));
    customer.setState("active");

    when(customersService.findAll())
      .thenReturn(Stream.of(customer));

    var uuid = customer.getUuid();

    given()
      .accept(ContentType.JSON)
      .when()
      .get("/customers")
      .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      .body("", instanceOf(List.class))
      .body(
        String.format(
          """
            findAll {
              it.uuid == '%s'
              && it.name == 'Tom Mayer'
              && it.birthdate == '2001-04-23'
              && it.state == 'active'
            }
            """,
          uuid
        ),
        is(not(empty()))
      );
  }

  @ParameterizedTest
  @ValueSource(strings = {
    """
              {
                "uuid": "12345678-1234-1234-1234-123456789012",
                "name": "Tom Mayer",
                "birthdate": "2001-04-23",
                "state": "active"
              }
      """,
  })
  void whenPostCustomersWithUuid_thenBadRequest(String body) {
    given()
      .contentType(ContentType.JSON)
      .body(body)
      .accept(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(400);

    verifyNoInteractions(customersService);

  }

  @Test
  void whenGetCustomerByIdForNonExisting_thenReturn404() {
    var uuid = UUID.randomUUID();
    when(customersService.findById(uuid))
      .thenReturn(Optional.empty());
    given()
      .accept(ContentType.JSON)
      .when()
      .get("/customers/{uuid}", uuid)
      .then()
      .statusCode(404);
  }


}
