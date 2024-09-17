package ph.dgtech.halalan.ballot.model;

import jakarta.persistence.*;
import lombok.*;
import ph.dgtech.halalan.ballot.model.enums.CandidateStatus;

import java.time.LocalDate;

@Entity
@Table(name = "t_candidates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String candidateId;

    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String photo;

    private String electionId;
    private String officeTitle;
    private String politicalParty;
    private Integer ballotNumber;

    private String votingDistrict;
    private String electoralDivision;

    private String emailAddress;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private CandidateStatus candidateStatus;
    private LocalDate nominationDate;
    private String createdBy;
    private String updatedBy;
    private String notes;
}
