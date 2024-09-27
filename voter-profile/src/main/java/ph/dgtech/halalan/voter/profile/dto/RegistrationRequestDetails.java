package ph.dgtech.halalan.voter.profile.dto;

import ph.dgtech.halalan.voter.profile.dto.info.AddressInfo;
import ph.dgtech.halalan.voter.profile.dto.info.PersonalInfo;
import ph.dgtech.halalan.voter.profile.dto.info.SystemInfo;
import ph.dgtech.halalan.voter.profile.dto.info.VotingInfo;

public record RegistrationRequestDetails(
        SystemInfo system,
        PersonalInfo personal,
        AddressInfo address,
        VotingInfo votingInfo
) {
}
