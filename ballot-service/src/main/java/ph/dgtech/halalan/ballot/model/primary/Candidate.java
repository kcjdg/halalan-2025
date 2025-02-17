package ph.dgtech.halalan.ballot.model.primary;

import jakarta.persistence.*;
import lombok.*;
import ph.dgtech.halalan.ballot.model.primary.enums.CandidateStatus;

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
    @Column(name = "candidate_id")
    private String candidateId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "photo")
    private String photo;

    @Column(name = "election_id")
    private String electionId;

    @Column(name = "office_title")
    private String officeTitle;

    @Column(name = "political_party")
    private String politicalParty;

    @Column(name = "ballot_number")
    private Integer ballotNumber;

    @Column(name = "voting_district")
    private String votingDistrict;

    @Column(name = "electoral_division")
    private String electoralDivision;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "candidate_status")
    @Enumerated(EnumType.STRING)
    private CandidateStatus candidateStatus;

    @Column(name = "nomination_date")
    private LocalDate nominationDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "notes")
    private String notes;

}
