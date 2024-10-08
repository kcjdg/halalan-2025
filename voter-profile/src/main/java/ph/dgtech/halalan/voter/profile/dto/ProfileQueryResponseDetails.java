package ph.dgtech.halalan.voter.profile.dto;

import ph.dgtech.halalan.voter.profile.dto.info.AddressInfo;
import ph.dgtech.halalan.voter.profile.dto.info.PersonalInfo;
import ph.dgtech.halalan.voter.profile.dto.info.VotingInfo;

public record ProfileQueryResponseDetails(
        String username,
        PersonalInfo personal,
        AddressInfo address,
        VotingInfo votingInfo
) {
}
