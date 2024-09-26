package ph.dgtech.halalan.polling.dto.address;

public record AddressRequest(
        String region,
        String municipality,
        String province,
        String barangay,
        String street) {
}
