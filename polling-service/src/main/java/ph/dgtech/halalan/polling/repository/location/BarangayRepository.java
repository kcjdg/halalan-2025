package ph.dgtech.halalan.polling.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import ph.dgtech.halalan.polling.model.location.Barangay;

public interface BarangayRepository extends JpaRepository<Barangay, Long> {
}
