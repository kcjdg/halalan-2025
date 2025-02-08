package ph.dgtech.halalan.voter.profile.integration.config;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;

import java.util.Map;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class KeyCloakTestContainers {


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
        registry.add("keycloak.auth-server-url", keycloak::getAuthServerUrl);
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
                        "client_id", "spring-microservice-client",
                        "client_secret","secret"
                ))
                .post(keycloak.getAuthServerUrl() + "/realms/halalan-voters/protocol/openid-connect/token")
                .then().assertThat().statusCode(200)
                .extract().path("access_token");
    }

    protected RequestSpecification getRequestSpecification() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        return new RequestSpecBuilder()
                .setPort(port)
                .addHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();
    }
}
