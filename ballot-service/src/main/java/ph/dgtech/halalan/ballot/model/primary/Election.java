package ph.dgtech.halalan.ballot.model.primary;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_elections")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "election_id")
    private String electionId;
    @Column(name = "election_name")
    private String electionName;
    @Column(name = "election_date")
    private LocalDate electionDate;
    @Column(name = "election_type")
    private String electionType;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_timestamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedTimestamp;
    private boolean flag;
}
