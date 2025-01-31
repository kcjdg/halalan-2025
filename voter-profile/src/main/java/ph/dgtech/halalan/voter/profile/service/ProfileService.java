package ph.dgtech.halalan.voter.profile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ph.dgtech.halalan.voter.profile.client.AddressClient;
import ph.dgtech.halalan.voter.profile.dto.ProfileQueryResponseDetails;
import ph.dgtech.halalan.voter.profile.dto.ProfileUpdateRequestDetails;
import ph.dgtech.halalan.voter.profile.dto.RegistrationRequestDetails;
import ph.dgtech.halalan.voter.profile.dto.RegistrationResponseDetails;
import ph.dgtech.halalan.voter.profile.dto.info.AddressInfo;
import ph.dgtech.halalan.voter.profile.dto.mappers.UserRepresentationMapper;
import ph.dgtech.halalan.voter.profile.exception.InternalClientException;
import ph.dgtech.halalan.voter.profile.exception.InvalidRequestFormatException;
import ph.dgtech.halalan.voter.profile.exception.NotFoundException;
import ph.dgtech.halalan.voter.profile.exception.UserAlreadyExistException;
import ph.dgtech.halalan.voter.profile.utils.KeyCloakConst;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final RealmResource realmResource;
    private final UserRepresentationMapper mapper;
    private final AddressClient addressClient;

    public RegistrationResponseDetails registerVoter(RegistrationRequestDetails request) {
        var user = mapper.mapFromRegistration(request);
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(createPasswordCredentials(request.system().password())));
        user.setGroups(List.of(KeyCloakConst.REGION.NCR.getGroupCode()));
        validateAddress(request.address());

        String userId;
        try(var response = realmResource.users().create(user)) {
            switch (response.getStatus()) {
                case 400 ->
                        throw new InvalidRequestFormatException("Invalid request format", response.readEntity(Object.class));
                case 403 -> throw new AccessDeniedException("Forbidden");
                case 409 -> throw new UserAlreadyExistException("Username or email already exists");
                case 201 -> log.info("User created successfully");
                default -> throw new RuntimeException("Failed to create user");
            }
            userId = getUserId(response.getLocation());
        }

        return new RegistrationResponseDetails(userId, request.system().username(), request.votingInfo().voterId(), request.personal().firstName(), request.personal().lastName());
    }

    public void updateVoter(ProfileUpdateRequestDetails request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var userOpt = getUserById(userId);
        if (userOpt.isEmpty()) throw new NotFoundException("Voter not found");
        var user = mapper.mapFromUpdate(request);
        validateAddress(request.address());
        realmResource.users().get(userId).update(user);
    }


    public ProfileQueryResponseDetails getVoterProfile() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var userOpt = getUserById(userId);
        if (userOpt.isEmpty()) throw new NotFoundException("Voter not found");
        return mapper.mapToQueryResponse(userOpt.get());
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


    private String getUserId(URI location) {
        return location.getPath().replaceAll(".*/([^/]+)$", "$1");
    }

    private void validateAddress(AddressInfo addressInfo){
        var addressResp = addressClient.validateAddress(addressInfo);
        switch (addressResp.getStatusCode()){
            case HttpStatus.OK -> log.info("Address validated successfully");
            case HttpStatus.INTERNAL_SERVER_ERROR ->
                throw new InternalClientException("Cannot verify address. Try again", addressInfo);
            default ->
                throw new InvalidRequestFormatException("Not a valid Address", addressInfo);
        }
    }

}
