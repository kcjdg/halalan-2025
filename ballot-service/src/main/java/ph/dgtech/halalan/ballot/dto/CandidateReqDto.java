package ph.dgtech.halalan.ballot.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CandidateReqDto(
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
