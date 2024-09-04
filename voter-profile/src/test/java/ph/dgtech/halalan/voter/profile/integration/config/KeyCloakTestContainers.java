package ph.dgtech.halalan.voter.profile.integration.config;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Map;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class KeyCloakTestContainers {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyCloakTestContainers.class.getName());

    @LocalServerPort
    private int port;

    static final KeycloakContainer keycloak;

    static {
        keycloak = new KeycloakContainer().withRealmImportFile("keycloak/realm-export.json");
        keycloak.start();
    }

    @PostConstruct
    public void init() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @DynamicPropertySource
    static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> keycloak.getAuthServerUrl() + "/realms/halalan-voters");
    }


    protected String getAccessToken(String username, String password) {
        return given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(Map.of(
                        "username", username,
                        "password", password,
                        "scope", "openid",
                        "grant_type", "password",
                        "client_id", "halalan-voters-api"
                ))
                .post(keycloak.getAuthServerUrl() + "/realms/halalan-voters/protocol/openid-connect/token")
                .then().assertThat().statusCode(200)
                .extract().path("access_token");
    }


    protected String getAccessTokenUsingClientCredentials() {
        return given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(Map.of(
                        "scope", "openid",
                        "grant_type", "client_credentials",
                        "client_id", "halalan-voters-api",
                        "client_secret",""
                ))
                .post(keycloak.getAuthServerUrl() + "/realms/halalan-voters/protocol/openid-connect/token")
                .then().assertThat().statusCode(200)
                .extract().path("access_token");
    }


}
