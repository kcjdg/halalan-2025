package ph.dgtech.halalan.polling.model.location;

import jakarta.persistence.*;
import lombok.*;
import ph.dgtech.halalan.polling.dto.AddressDetails;

import java.util.Set;

@Entity
@Table(name = "t_region")
@Data
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long id;

    @Column(name = "region_name")
    private String name;

    @Column(name = "region_description")
    private String description;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Province> provinces;
}
