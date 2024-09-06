package ph.dgtech.halalan.voter.profile.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ph.dgtech.halalan.voter.profile.dto.ProfileUpdateRequestDetails;
import ph.dgtech.halalan.voter.profile.dto.RegistrationRequestDetails;
import ph.dgtech.halalan.voter.profile.dto.RegistrationResponseDetails;
import ph.dgtech.halalan.voter.profile.exception.InvalidRequestFormatException;
import ph.dgtech.halalan.voter.profile.exception.NotFoundException;
import ph.dgtech.halalan.voter.profile.exception.UserAlreadyExistException;
import ph.dgtech.halalan.voter.profile.utils.KeyCloakConst;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static ph.dgtech.halalan.voter.profile.dto.info.PersonalInfo.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final RealmResource realmResource;
    private final ConversionService conversionService;

    @SneakyThrows
    public RegistrationResponseDetails registerVoter(RegistrationRequestDetails request) {
        var user = new UserRepresentation();
        user.setUsername(request.system().username());
        user.setFirstName(request.personal().firstName());
        user.setLastName(request.personal().lastName());
        user.setEmail(request.personal().email());
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(createPasswordCredentials(request.system().password())));
        user.setGroups(List.of(KeyCloakConst.REGION.NCR.getGroupCode()));
        user.setAttributes(
                setFields(
                        request.votingInfo().voterId(),
                        request.personal().dob(),
                        request.personal().middleName(),
                        request.personal().gender()
                )
        );
        var response = realmResource.users().create(user);
        switch (response.getStatus()) {
            case 400 ->
                    throw new InvalidRequestFormatException("Invalid request format", response.readEntity(Object.class));
            case 403 -> throw new AccessDeniedException("Forbidden");
            case 409 -> throw new UserAlreadyExistException("Username or email already exists");
            case 201 -> log.info("User created successfully");
            default -> throw new RuntimeException("Failed to create user");
        }

        return new RegistrationResponseDetails(getUserId(response.getLocation()), request.system().username(), request.votingInfo().voterId(), request.personal().firstName(), request.personal().lastName());
    }

    public void updateVoter(ProfileUpdateRequestDetails request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var userOpt = getUserById(userId);
        if (userOpt.isEmpty()) throw new NotFoundException("Voter not found");
        var user = userOpt.get();
        user.setFirstName(request.personal().firstName());
        user.setLastName(request.personal().lastName());
        user.setEmail(request.personal().email());
        Map<String, List<String>> fieldMap = setFields(
                request.votingInfo().voterId(),
                request.personal().dob(),
                request.personal().middleName(),
                request.personal().gender()
        );
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


    private Map<String, List<String>> setFields(String voterId, LocalDate dob,
                                                String middleName, String gender) {
        Map<String, List<String>> fields = new HashMap<>();
        fields.put("voterId", List.of(voterId));
        fields.put("dob", List.of(dob.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        fields.put("middleName", List.of(middleName));
        fields.put("gender", List.of(Gender.fromString(gender).name()));
        return fields;
    }


    private String getUserId(URI location) {
        return location.getPath().replaceAll(".*/([^/]+)$", "$1");
    }


}
