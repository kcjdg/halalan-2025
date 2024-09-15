package ph.dgtech.halalan.ballot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "t_candidates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String photo;

    private UUID electionId;
    private String officeTitle;
    private String politicalParty;
    private Integer ballotNumber;

    private String votingDistrict;
    private String electoralDivision;

    private String emailAddress;
    private String phoneNumber;

    private String candidateStatus;
    private LocalDate nominationDate;
    private String createdBy;
    private String updatedBy;
    private String notes;
}
