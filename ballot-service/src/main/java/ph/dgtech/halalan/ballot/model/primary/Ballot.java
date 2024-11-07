package ph.dgtech.halalan.ballot.model.primary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_ballots")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Ballot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ballotId;
    private String voterId;
    private String electionId;
    private String districtId;
    private String electoralDivision;
    private String pollingStation;
    private LocalDateTime generatedTimestamp;
    private String ballotStatus;
    private String votingMethod;
    private String referendumQuestions;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime updateTimestamp;

}
