package ph.dgtech.halalan.voter.profile.dto.info;

import jakarta.validation.constraints.NotNull;

public record VotingInfo(
        @NotNull
        String voterId
        ){
}
