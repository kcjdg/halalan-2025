package ph.dgtech.halalan.voter.profile.integration;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import ph.dgtech.halalan.voter.profile.integration.config.KeyCloakTestContainers;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VoterProfileApplicationTest extends KeyCloakTestContainers {

    private static String PATH = "/voter-profile/";

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


    }

}
