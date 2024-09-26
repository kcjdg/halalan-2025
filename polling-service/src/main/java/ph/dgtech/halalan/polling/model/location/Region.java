package ph.dgtech.halalan.polling.model.location;

import jakarta.persistence.*;
import lombok.*;
import ph.dgtech.halalan.polling.dto.address.AddressDetails;

import java.util.Set;

@Entity

@SqlResultSetMapping(
        name = "AddressDetailsMapping",
        classes = @ConstructorResult(
                targetClass = AddressDetails.class,
                columns = {
                        @ColumnResult(name = "region", type = String.class),
                        @ColumnResult(name = "province", type = String.class),
                        @ColumnResult(name = "municipality", type = String.class),
                        @ColumnResult(name = "barangay", type = String.class)
                }
        )
)
@NamedStoredProcedureQuery(
        name = "getLocationDetails",
        procedureName = "GetAddressDetails",
        resultSetMappings = "AddressDetailsMapping",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "regionName", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "provinceName", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "municipalityName", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "barangayName", type = String.class)
        }
)


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
