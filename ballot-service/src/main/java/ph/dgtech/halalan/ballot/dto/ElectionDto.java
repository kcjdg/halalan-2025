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

        public ElectionDto(String electionName, LocalDate electionDate, String electionType) {
                this(null, electionName, electionDate, electionType, null, null, null, null, true);
        }

        public ElectionDto(String electionName, LocalDate electionDate, String electionType, boolean isActive) {
                this(null, electionName, electionDate, electionType, null, null, null, null, isActive);
        }

}
