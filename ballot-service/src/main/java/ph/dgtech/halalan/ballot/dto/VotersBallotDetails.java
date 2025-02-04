package ph.dgtech.halalan.ballot.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record VotersBallotDetails(
        String voterId,
        String electionId,
        LocalDate electionDate,
        String districtId,
        String electoralDivision
) {
}
