package ph.dgtech.halalan.polling.dto;

import jakarta.persistence.ColumnResult;

public record AddressDetails(
        String region,
        String province,
        String municipality,
        String barangay) {
}
