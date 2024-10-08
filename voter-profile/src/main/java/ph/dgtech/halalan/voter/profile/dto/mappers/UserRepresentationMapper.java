package ph.dgtech.halalan.voter.profile.dto.mappers;

import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.*;
import ph.dgtech.halalan.voter.profile.dto.ProfileQueryResponseDetails;
import ph.dgtech.halalan.voter.profile.dto.ProfileUpdateRequestDetails;
import ph.dgtech.halalan.voter.profile.dto.RegistrationRequestDetails;
import ph.dgtech.halalan.voter.profile.dto.info.AddressInfo;
import ph.dgtech.halalan.voter.profile.dto.info.PersonalInfo;
import ph.dgtech.halalan.voter.profile.dto.info.SystemInfo;
import ph.dgtech.halalan.voter.profile.dto.info.VotingInfo;
import ph.dgtech.halalan.voter.profile.utils.KeyCloakConst;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiFunction;

import static ph.dgtech.halalan.voter.profile.dto.info.PersonalInfo.*;


@Mapper(componentModel = "spring")
public interface UserRepresentationMapper {


    @Mapping(target = ".", source = "personal")
    @Mapping(target = ".", source = "system")
    UserRepresentation mapFromRegistration(RegistrationRequestDetails source);

    @Mapping(target = ".", source = "personal")
    @Mapping(target = ".", source = "votingInfo")
    UserRepresentation mapFromUpdate(ProfileUpdateRequestDetails source);

    @Mapping(target = "personal", source = ".", qualifiedByName = "toPersonalInfo")
    @Mapping(target = "address", source = ".", qualifiedByName = "toAddressInfo")
    @Mapping(target = "votingInfo", source = ".", qualifiedByName = "toVotingInfo")
    ProfileQueryResponseDetails mapToQueryResponse(UserRepresentation source);


    @AfterMapping
    default void setAttributes(@MappingTarget UserRepresentation user, RegistrationRequestDetails request) {
        user.setAttributes(toAttributes(request.votingInfo(), request.personal(), request.address()));
    }

    @AfterMapping
    default void setAttributes(@MappingTarget UserRepresentation user, ProfileUpdateRequestDetails request) {
        var now = LocalDateTime.now();
        Map<String, List<String>> map = new HashMap<>();
        map.put("lastUpdateDateTime", List.of(now.format(KeyCloakConst.formatter)));
        map.putAll(toAttributes(request.votingInfo(), request.personal(), request.address()));
        user.setAttributes(Collections.unmodifiableMap(map));
    }

    @Named("toPersonalInfo")
    default PersonalInfo toPersonalInfo(UserRepresentation user) {
        return new PersonalInfo(
                user.getFirstName(),
                getSafeAttribute.apply("middleName", user.getAttributes()),
                user.getLastName(),
                Gender.valueOf(getSafeAttribute.apply("gender", user.getAttributes())).getCode(),
                LocalDate.parse(getSafeAttribute.apply("dob", user.getAttributes())),
                user.getEmail()
        );
    }

    @Named("toAddressInfo")
    default AddressInfo toAddressInfo(UserRepresentation user) {
        return new AddressInfo(
                getSafeAttribute.apply("region", user.getAttributes()),
                getSafeAttribute.apply("province", user.getAttributes()),
                getSafeAttribute.apply("municipality", user.getAttributes()),
                getSafeAttribute.apply("barangay", user.getAttributes())
        );
    }

    @Named("toVotingInfo")
    default VotingInfo toVotingInfo(UserRepresentation user) {
        return new VotingInfo(
                getSafeAttribute.apply("voterId", user.getAttributes())
        );
    }

    BiFunction<String, Map<String, List<String>>, String> getSafeAttribute =
            (attributeName, attributes) ->
                    Optional.ofNullable(attributes)
                            .map(attrs -> attrs.containsKey(attributeName) ?
                                    attributes.get(attributeName) : null)  // Get the list of values for the attribute
                            .filter(values -> !values.isEmpty())     // Ensure the list is not empty
                            .map(List::getFirst)                    // Get the first value from the list
                            .orElse(null);


    default Map<String, List<String>> toAttributes(VotingInfo votingInfo, PersonalInfo personal, AddressInfo address) {
        return Map.of(
                "voterId", List.of(votingInfo.voterId()),
                "dob", List.of(personal.dob().format(DateTimeFormatter.ISO_LOCAL_DATE)),
                "middleName", List.of(personal.middleName()),
                "gender", List.of(Gender.fromString(personal.gender()).name()),
                "region", List.of(address.region()),
                "province", List.of(address.province()),
                "municipality", List.of(address.municipality()),
                "barangay", List.of(address.barangay())
        );
    }
}
