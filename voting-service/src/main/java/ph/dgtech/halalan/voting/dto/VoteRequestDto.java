package ph.dgtech.halalan.voting.dto;

import java.util.List;
import java.util.Map;

public record VoteRequestDto(
                       String electionId,
                       String pollingStationId,
                       String deviceId,
                       String geoLocation,
                       String language,
                       List<CandidateRequestDto> votedCandidates) {


    public record CandidateRequestDto(String positionId, String candidateId){

    }
}
