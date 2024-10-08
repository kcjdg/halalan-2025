package ph.dgtech.halalan.polling.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.dgtech.halalan.polling.model.location.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}
