package ph.dgtech.halalan.voter.profile.integration;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import ph.dgtech.halalan.voter.profile.integration.config.KeyCloakTestContainers;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VoterProfileApplicationTest extends KeyCloakTestContainers {

    private static String PATH = "/v1";

    @Test
    void givenUnauthenticatedUser_whenAccess_shouldReturnUnAuthorized() {
        given()
                .when()
                .get(PATH)
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void givenAuthenticatedUser_whenAccess_shouldReturnOK() {
        given()
                .auth().oauth2(getAccessToken("jane.doe@halalan-voters.com", "s3cr3t"))
                .when()
                .get(PATH)
                .then()
                .statusCode(200);
    }

    @Test
    public void givenClientCredentials_whenRegister_shouldReturn201() {
        String json = """
                {
                    "username": "uniquename",
                    "firstName": "john",
                    "middleName":"deep",
                    "lastName": "doe",
                    "password": "123",
                    "email": "uniquename@gmail.com",
                    "dob": "1993-01-01",
                    "gender": "M",
                    "voterId": "ID-0011"
                }
                """;
        given(getRequestSpecification())
                .auth().oauth2(getAccessTokenUsingClientCredentials())
                .body(json)
                .when()
                .post(PATH + "/halalan/register")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .log().ifValidationFails();
    }

}
