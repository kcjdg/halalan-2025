package ph.dgtech.halalan.voter.profile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ph.dgtech.halalan.voter.profile.dto.VoterRequestDetails;
import ph.dgtech.halalan.voter.profile.dto.VoterResponseDetails;
import ph.dgtech.halalan.voter.profile.exception.InvalidRequestFormatException;
import ph.dgtech.halalan.voter.profile.exception.NotFoundException;
import ph.dgtech.halalan.voter.profile.exception.UserAlreadyExistException;
import ph.dgtech.halalan.voter.profile.utils.KeyCloakConst;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static ph.dgtech.halalan.voter.profile.dto.VoterRequestDetails.Gender;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final RealmResource realmResource;

    public VoterResponseDetails registerVoter(VoterRequestDetails request) {
        var user = new UserRepresentation();
        user.setUsername(request.username());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(createPasswordCredentials(request.password())));
        user.setGroups(List.of(KeyCloakConst.REGION.NCR.getGroupCode()));
        user.setAttributes(setFields(request));
        var response = realmResource.users().create(user);
        switch (response.getStatus()) {
            case 409 -> throw new UserAlreadyExistException("Username or email already exists");
            case 400 ->
                    throw new InvalidRequestFormatException("Invalid request format", response.readEntity(Object.class));
            case 201 -> log.info("User created successfully");
            default -> throw new RuntimeException("Failed to create user");
        }

        return new VoterResponseDetails(getUserId(response.getLocation()), request.username(), request.voterId(), request.firstName(), request.lastName());
    }

    public void updateVoter(VoterRequestDetails request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var userOpt = getUserById(userId);
        if (!userOpt.isPresent()) throw new NotFoundException("Voter not found");
        var user = userOpt.get();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        Map<String, List<String>> fieldMap = setFields(request);
        final var now = LocalDateTime.now();
        fieldMap.put("lastUpdateDateTime", List.of(now.format(KeyCloakConst.formatter)));
        user.setAttributes(fieldMap);
        realmResource.users().get(userId).update(user);
    }


    public Optional<UserRepresentation> getUserById(String userId) {
        try {
            var userResource = realmResource.users().get(userId);
            return Optional.ofNullable(userResource.toRepresentation());
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public static CredentialRepresentation createPasswordCredentials(String password) {
        var passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }


    private Map<String, List<String>> setFields(VoterRequestDetails request) {
        Map<String, List<String>> fields = new HashMap<>();
        fields.put("voterId", List.of(request.voterId()));
        fields.put("dob", List.of(request.dob().format(DateTimeFormatter.ISO_LOCAL_DATE)));
        fields.put("middleName", List.of(request.middleName()));
        fields.put("gender", List.of(Gender.fromString(request.gender()).name()));
        return fields;
    }


    private String getUserId(URI location) {
        return location.getPath().replaceAll(".*/([^/]+)$", "$1");
    }


}
