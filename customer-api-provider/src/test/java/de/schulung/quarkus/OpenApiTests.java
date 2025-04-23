package de.schulung.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class OpenApiTests {

  /*
   * GET /openapi.yml
   * -> 200 OK
   */

  @Test
  void whenGetOpenApi_thenReturn200() {
    given()
      .when()
      .get("/openapi.yml")
      .then()
      .statusCode(200);
  }

}
