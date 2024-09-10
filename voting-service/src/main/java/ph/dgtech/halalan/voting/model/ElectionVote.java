package ph.dgtech.halalan.voting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("election-vote")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ElectionVote {
    @Id
    String voteId;
    String voterId;
    String electionId;
    LocalDateTime voteDateTime;
    Boolean isValid;
    String pollingStationId;
    String deviceId;
    String geolocation;
    String language;
    List<CandidatePosition> votedCandidates;
}