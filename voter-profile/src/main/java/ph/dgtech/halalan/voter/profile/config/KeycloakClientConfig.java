package ph.dgtech.halalan.voter.profile.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

@Configuration
public class KeycloakClientConfig {
    private final KeyCloakPropsConfig keycloakPropsConfig;

    public KeycloakClientConfig(KeyCloakPropsConfig keycloakPropsConfig) {
        this.keycloakPropsConfig = keycloakPropsConfig;
    }

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
            .grantType(CLIENT_CREDENTIALS)
            .serverUrl(keycloakPropsConfig.getAuthServerUrl())
            .realm(keycloakPropsConfig.getRealm())
            .clientId(keycloakPropsConfig.getResource())
            .clientSecret(keycloakPropsConfig.getCredentials().getSecret())
            .build();
    }

    @Bean
    public RealmResource realmResource(Keycloak keycloak) {
        return keycloak.realm(keycloakPropsConfig.getRealm());
    }

}
