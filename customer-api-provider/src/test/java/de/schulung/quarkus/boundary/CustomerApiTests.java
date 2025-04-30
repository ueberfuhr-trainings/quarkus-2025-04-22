package de.schulung.quarkus.boundary;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestTransaction
class CustomerApiTests {

  @Test
  void whenGetCustomers_thenOk() {
    var response = given()
      .contentType(ContentType.JSON)
      .body("""
            {
              "name": "Tom Mayer",
              "birthdate": "2001-04-23",
              "state": "active"
            }
        """)
      .accept(ContentType.JSON)
      .when()
      .post("/customers");
    response
      .then()
      .statusCode(201)
      .header("Location", is(notNullValue()));

    var uuid = response.body().jsonPath().getString("uuid");

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

  @Test
  void whenGetCustomersInvalidAccept_thenNotAcceptable() {
    given()
      .accept(ContentType.XML)
      .when()
      .get("/customers")
      .then()
      .statusCode(406);
  }

  @Test
  void whenPostCustomers_thenCreated() {
    given()
      .contentType(ContentType.JSON)
      .body("""
            {
              "name": "Tom Mayer",
              "birthdate": "2001-04-23",
              "state": "active"
            }
        """)
      .accept(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(201)
      .contentType(ContentType.JSON)
      .body("name", is(equalTo("Tom Mayer")))
      .body("birthdate", is(equalTo("2001-04-23")))
      .body("state", is(equalTo("active")))
      .body("uuid", is(notNullValue()))
      .header("Location", is(notNullValue()));
  }

  @Test
  void whenPostCustomersWithInvalidAccept_thenNotAcceptable() {
    given()
      .contentType(ContentType.JSON)
      .body("""
            {
              "name": "Tom Mayer",
              "birthdate": "2001-04-23",
              "state": "active"
            }
        """)
      .accept(ContentType.XML)
      .when()
      .post("/customers")
      .then()
      .statusCode(406);
  }

  @Test
  void whenPostCustomersWithXmlBody_thenUnsupportedMediaType() {
    given()
      .contentType(ContentType.XML)
      .body("""
            <test/>
        """)
      .accept(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(415);
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
    """
              {
                "name": "Tom Mayer",
                "birthdate": "2001-04-23",
                "state": "active",
                "gelbekatze": "test"
              }
      """,
    """
              {
                "name": "Tom Mayer",
                "birthdate": "gelbekatze",
                "state": "active"
              }
      """,
    """
              {
                "birthdate": "2001-04-23",
                "state": "active"
              }
      """,
    """
              {
                "name": "Tom Mayer",
                "state": "active"
              }
      """,
    """
              {
                "name": "T",
                "birthdate": "2001-04-23",
                "state": "active"
              }
      """,
    """
              {
                "name": "T0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789",
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
  }

  @Test
  void whenPostCustomers_thenGetCustomerByIdSuccessful() {
    var response = given()
      .contentType(ContentType.JSON)
      .body("""
            {
              "name": "Tom Mayer",
              "birthdate": "2001-04-23",
              "state": "active"
            }
        """)
      .accept(ContentType.JSON)
      .when()
      .post("/customers");
    response
      .then()
      .statusCode(201)
      .header("Location", is(notNullValue()));

    var location = response.getHeader("Location");
    var uuid = response.body().jsonPath().getString("uuid");

    given()
      .accept(ContentType.JSON)
      .when()
      .get(location)
      .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      .body("name", is(equalTo("Tom Mayer")))
      .body("birthdate", is(equalTo("2001-04-23")))
      .body("state", is(equalTo("active")))
      .body("uuid", is(equalTo(uuid)));
  }

}
