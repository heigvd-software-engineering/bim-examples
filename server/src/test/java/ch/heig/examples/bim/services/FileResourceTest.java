package ch.heig.examples.bim.services;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class FileResourceTest {

    private final static String PATH = "/files";

    @Test
    public void findAllFilesShouldWork() {
        given()
          .when().get(PATH)
          .then()
             .statusCode(200);
    }

}