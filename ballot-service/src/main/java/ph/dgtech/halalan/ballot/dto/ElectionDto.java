package ph.dgtech.halalan.ballot.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ElectionDto(
        String electionId,
        String electionName,
        LocalDate electionDate,
        String electionType,
        LocalDateTime createdAt,
        String createdBy,
        String updatedBy,
        LocalDateTime updatedTimestamp,
        boolean flag
        ) {
}
