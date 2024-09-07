package ph.dgtech.halalan.voter.profile.integration;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import ph.dgtech.halalan.voter.profile.integration.config.KeyCloakTestContainers;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VoterProfileApplicationTest extends KeyCloakTestContainers {

    private static final String PATH = "/v1";

    @Test
    void givenUnauthenticatedUser_whenAccess_shouldReturnUnAuthorized() {
        given().when().get(PATH).then().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void givenAuthenticatedUser_whenAccess_shouldReturnOK() {
        given()
                .auth().oauth2(getAccessToken("jane.doe@halalan-voters.com", "s3cr3t"))
                .when()
                .get(PATH).then()
                .statusCode(HttpStatus.OK.value())
                .body("personal.firstName", Matchers.equalTo("Jane"),
                        "personal.middleName", Matchers.equalTo("Walker"),
                        "personal.lastName", Matchers.equalTo("Doe"),
                        "votingInfo.voterId", Matchers.equalTo("ID-0001"),
                        "personal.gender", Matchers.equalTo("M"),
                        "personal.dob", Matchers.equalTo("1993-01-01"),
                        "personal.email", Matchers.equalTo("jane.doe@halalan-voters.com"),
                        "username", Matchers.equalTo("janedoe")
                        );
    }


    @Test
    public void givenUser_whenUpdated_shouldReturnNoContent() {
        String json = """
                {
                   "personal": {
                   "firstName": "Jane",
                    "middleName": "Deep",
                    "lastName": "Doe",
                    "email": "jane.doe@halalan-voters.com",
                    "dob": "1993-01-01",
                    "gender": "F"
                   },
                    "votingInfo": {
                    "voterId": "ID-0012"
                   }
                }
                
                """;
        given(getRequestSpecification()).auth().oauth2(getAccessToken("jane.doe@halalan-voters.com", "s3cr3t"))
                .body(json).when()
                .put(PATH + "/")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void givenClientCredentials_whenRegister_shouldReturnCreatedStatus() {
        String json = """
                {
                     "system": {
                       "username": "johnwill",
                       "password": "123"
                     },
                     "personal": {
                       "firstName": "John",
                       "middleName": "Everson",
                       "lastName": "Williams",
                       "email": "johnwill@gmail.com",
                       "dob": "1993-01-01",
                       "gender": "M"
                     },
                     "votingInfo": {
                       "voterId": "ID-0011"
                     }
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
