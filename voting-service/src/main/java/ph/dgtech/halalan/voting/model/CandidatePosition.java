package ph.dgtech.halalan.voting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("candidate-position")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CandidatePosition {
    @Id
    String positionId;
    String candidateId;
    String candidateName;
}
