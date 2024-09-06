package ph.dgtech.halalan.voter.profile.dto.mappers;

import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.*;
import ph.dgtech.halalan.voter.profile.dto.ProfileUpdateRequestDetails;
import ph.dgtech.halalan.voter.profile.dto.RegistrationRequestDetails;
import ph.dgtech.halalan.voter.profile.dto.info.PersonalInfo;
import ph.dgtech.halalan.voter.profile.dto.info.SystemInfo;
import ph.dgtech.halalan.voter.profile.dto.info.VotingInfo;
import ph.dgtech.halalan.voter.profile.utils.KeyCloakConst;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ph.dgtech.halalan.voter.profile.dto.info.PersonalInfo.*;


@Mapper(componentModel = "spring")
public interface UserRepresentationMapper {


    @Mapping(target = ".", source = "personal")
    @Mapping(target = ".", source = "system")
    UserRepresentation mapFromRegistration(RegistrationRequestDetails source);

    @Mapping(target = ".", source = "personal")
    @Mapping(target = ".", source = "votingInfo")
    UserRepresentation mapFromUpdate(ProfileUpdateRequestDetails source);


    @AfterMapping
    default void setAttributes(@MappingTarget UserRepresentation user, RegistrationRequestDetails request) {
        user.setAttributes(toAttributes(request.votingInfo(), request.personal()));
    }

    @AfterMapping
    default void setAttributes(@MappingTarget UserRepresentation user, ProfileUpdateRequestDetails request) {
        var now = LocalDateTime.now();
        Map<String, List<String>> map = new HashMap<>();
        map.put("lastUpdateDateTime", List.of(now.format(KeyCloakConst.formatter)));
        map.putAll(toAttributes(request.votingInfo(), request.personal()));
        user.setAttributes(Collections.unmodifiableMap(map));
    }

    default Map<String, List<String>> toAttributes(VotingInfo votingInfo, PersonalInfo personal) {
        return Map.of(
                "voterId", List.of(votingInfo.voterId()),
                "dob", List.of(personal.dob().format(DateTimeFormatter.ISO_LOCAL_DATE)),
                "middleName", List.of(personal.middleName()),
                "gender", List.of(Gender.fromString(personal.gender()).name())
        );
    }
}
