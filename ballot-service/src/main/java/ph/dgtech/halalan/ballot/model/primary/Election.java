package ph.dgtech.halalan.ballot.model.primary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Year;

@Entity
@Table(name = "t_election")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String electionId;
    private String electionName;
    private LocalDate electionDate;
    private Year electionYear;
    private String electionType;
}
