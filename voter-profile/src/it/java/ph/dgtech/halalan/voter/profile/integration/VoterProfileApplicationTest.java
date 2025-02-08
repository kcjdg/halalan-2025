package ph.dgtech.halalan.voter.profile.integration;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import ph.dgtech.halalan.voter.profile.dto.info.AddressInfo;
import ph.dgtech.halalan.voter.profile.integration.config.KeyCloakTestContainers;
import ph.dgtech.halalan.voter.profile.integration.stubs.AddressClientStub;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
class VoterProfileApplicationTest extends KeyCloakTestContainers {

    private static final String PATH = "/v1";

    @Test
    void givenUnauthenticatedUser_whenAccess_shouldReturnUnAuthorized() {
        given().when().get(PATH).then().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
     void givenAuthenticatedUser_whenAccess_shouldReturnOK() {
        given()
                .auth().oauth2(getAccessToken("johnywalker@halalan-voters.com", "s3cr3t"))
                .when()
                .get(PATH).then()
                .statusCode(HttpStatus.OK.value())
                .body("personal.firstName", Matchers.equalTo("John"),
                        "personal.middleName", Matchers.equalTo("Deep"),
                        "personal.lastName", Matchers.equalTo("Walker"),
                        "votingInfo.voterId", Matchers.equalTo("ID-0002"),
                        "personal.gender", Matchers.equalTo("M"),
                        "personal.dob", Matchers.equalTo("1993-01-02"),
                        "personal.email", Matchers.equalTo("johnywalker@halalan-voters.com"),
                        "address.region", Matchers.equalTo("Region IV-A"),
                        "address.province", Matchers.equalTo("Laguna"),
                        "address.municipality", Matchers.equalTo("Calamba City"),
                        "address.barangay", Matchers.equalTo("Banlic"),
                        "username", Matchers.equalTo("johnywalker")
                        );
    }


    @Test
     void givenUpdatedUserDetails_whenPutMethodCalled_shouldReturnNoContent() {
        /**
         * Updated user details
         * middleName: Deep
         * gender: F
         * address.barangay: Bucal
         */
        AddressInfo address = new AddressInfo( "Region IV-A", "Laguna", "Calamba City", "Bucal");
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
                    "address" : {
                        "region": "%s",
                        "province": "%s",
                        "municipality":"%s",
                        "barangay":"%s"
                     },
                    "votingInfo": {
                    "voterId": "ID-0012"
                   }
                }
                """.formatted(address.region(), address.province(), address.municipality(), address.barangay());

        AddressClientStub.stubValidateAddress(address);
        given(getRequestSpecification()).auth().oauth2(getAccessToken("jane.doe@halalan-voters.com", "s3cr3t"))
                .body(json).when()
                .put(PATH + "/")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
     void givenClientCredentials_whenRegister_shouldReturnCreatedStatus() {
        AddressInfo address = new AddressInfo( "Region IV-A", "Laguna", "Calamba City", "Bucal");
        AddressClientStub.stubValidateAddress(address);
        given(getRequestSpecification())
                .auth().oauth2(getAccessTokenUsingClientCredentials())
                .body(createJson(address))
                .when()
                .post(PATH + "/halalan/register")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .log().ifValidationFails();
    }

    @Test
     void givenIncorrectAddress_whenRegister_shouldReturnBadRequest() {
        AddressInfo address = new AddressInfo( "RegionTest", "ProvinceTest", "CityTest", "BarangayTest"); //Incorrect Address
        AddressClientStub.stubAddressNotFound(address);
        given(getRequestSpecification())
                .auth().oauth2(getAccessTokenUsingClientCredentials())
                .body(createJson(address))
                .when()
                .post(PATH + "/halalan/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .log().ifValidationFails();
    }


    @Test
     void givenIncorrectAddress_whenRegisterAndServerUnavailable_shouldReturnInternalServerError() {
        AddressInfo address = new AddressInfo( "Region IV-A", "Laguna", "Calamba City", "Bucal");
        AddressClientStub.stubInternalServer(address);
        given(getRequestSpecification())
                .auth().oauth2(getAccessTokenUsingClientCredentials())
                .body(createJson(address))
                .when()
                .post(PATH + "/halalan/register")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .log().ifValidationFails();
    }

    private String createJson(AddressInfo address) {
        return  """
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
                      "address" : {
                        "region": "%s",
                        "province": "%s",
                        "municipality":"%s",
                        "barangay":"%s"
                     },
                     "votingInfo": {
                       "voterId": "ID-0011"
                     }
                   }
                """.formatted(address.region(), address.province(), address.municipality(), address.barangay());
    }

}
