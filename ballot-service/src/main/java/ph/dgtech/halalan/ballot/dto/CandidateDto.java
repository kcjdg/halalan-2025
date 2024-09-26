package ph.dgtech.halalan.ballot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public record CandidateDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String candidateId,
        String firstName,
        String middleName,
        String lastName,
        LocalDate dateOfBirth,
        String gender,
        String photo,

        UUID electionId,
        String officeTitle,
        String politicalParty,
        Integer ballotNumber,

        String campaignWebsite,
        String campaignSlogan,
        String campaignPlatform,

        String votingDistrict,
        String electoralDivision,

        String emailAddress,
        String phoneNumber,

        String candidateStatus,
        Integer votesReceived,
        LocalDate nominationDate,

        String createdBy,
        String updatedBy,
        String notes
) {}
