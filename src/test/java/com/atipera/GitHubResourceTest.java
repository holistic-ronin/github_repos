package com.atipera;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
class GitHubResourceTest {

  @Test
  void testGetRepositoriesHappyPath() {
    final var responseBody =
        given()
            .accept("application/json")
            .when()
            .get("/repositories/octocat")
            .then()
            .statusCode(200)
            .log()
            .all()
            .extract()
            .asString();

    System.out.println("Response Body: " + responseBody);

    given()
        .accept("application/json")
        .when()
        .get("/repositories/octocat")
        .then()
        .statusCode(200)
        .body("$", not(empty()));
  }
}
