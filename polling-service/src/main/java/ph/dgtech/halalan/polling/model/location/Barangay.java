package ph.dgtech.halalan.polling.model.location;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "t_barangay")
@Data
public class Barangay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barangay_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "municipality_id", nullable = false)
    private Municipality municipality;

    @Column(name = "barangay_name")
    private String name;
}
