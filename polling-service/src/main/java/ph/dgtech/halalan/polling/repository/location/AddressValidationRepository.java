package ph.dgtech.halalan.polling.repository.location;


import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ph.dgtech.halalan.polling.model.location.Region;

import java.util.List;

@Repository
public interface AddressValidationRepository extends CrudRepository<Region, Long> {

        @Procedure(value = "GetLocationDetails", outputParameterName = "addressDetailsMapping")
        List<Object[]> getLocationDetails(
                @Param("regionName") String regionName,
                @Param("provinceName") String provinceName,
                @Param("municipalityName") String municipalityName,
                @Param("barangayName") String barangayName
        );
}
