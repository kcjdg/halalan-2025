package ph.dgtech.halalan.voter.registration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import ph.dgtech.halalan.voter.registration.config.KeyCloakPropsConfig;
import ph.dgtech.halalan.voter.registration.dto.RegistrationRequest;
import ph.dgtech.halalan.voter.registration.dto.RegistrationResponse;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final Keycloak keycloak;
    private final KeyCloakPropsConfig keycloakPropsConfig;

    public RegistrationResponse registerVoter(RegistrationRequest request) {
        RealmResource realmResource = keycloak.realm(keycloakPropsConfig.getRealm());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.username());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(createPasswordCredentials(request.password())));
        Response response = realmResource.users().create(user);
        log.info("response: {}", response.getStatus());
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user");
        }
        return new RegistrationResponse(request.voterId());
    }

    public static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }


}
