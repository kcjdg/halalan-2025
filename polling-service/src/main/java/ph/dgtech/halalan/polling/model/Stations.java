package ph.dgtech.halalan.polling.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_stations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Stations {

    @Id
    private String stationId;
    private String clusterId;
    private String districtId;
    private LocalDateTime pollingStart;
    private LocalDateTime pollingEnd;
    private Boolean isAccessible;
    private String stationStatus;

}
