package ph.dgtech.halalan.polling.repository.location;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import ph.dgtech.halalan.polling.dto.address.AddressDetails;
import ph.dgtech.halalan.polling.model.location.Region;

@Repository
public interface AddressValidationRepository extends JpaRepository<Region, Long> {

    @Procedure(name = "getLocationDetails")
    AddressDetails getLocationDetails(String regionName, String provinceName,
                                      String municipalityName, String barangayName
    );


}
