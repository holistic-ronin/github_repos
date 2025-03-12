package com.atipera;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;

@QuarkusTest
public class GitHubResourceTest {

  private static WireMockServer wireMockServer;

  @BeforeAll
  static void setup() {
    wireMockServer = new WireMockServer(options().dynamicPort());
    wireMockServer.start();

    System.setProperty("github-api/mp-rest/url", "http://localhost:" + wireMockServer.port());

    wireMockServer.stubFor(WireMock.get(WireMock.urlPathMatching("/users/octocat/repos"))
            .willReturn(WireMock.aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("[{\"name\":\"repo1\",\"fork\":false,\"owner\":{\"login\":\"octocat\"}}]")));

    wireMockServer.stubFor(WireMock.get(WireMock.urlPathMatching("/repos/octocat/repo1/branches"))
            .willReturn(WireMock.aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("[{\"name\":\"main\",\"commit\":{\"sha\":\"abc123\"}}]")));
  }

  @AfterAll
  static void teardown() {
    if (wireMockServer != null) {
      wireMockServer.stop();
    }
  }

  @Test
  public void testGetRepositoriesHappyPath() {
    final var responseBody =
            given()
                    .accept("application/json")
                    .when()
                    .get("/repositories/octocat")
                    .then()
                    .statusCode(200)
                    .log().all()
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
