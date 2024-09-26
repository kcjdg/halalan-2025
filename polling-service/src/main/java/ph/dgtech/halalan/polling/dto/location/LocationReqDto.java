package ph.dgtech.halalan.polling.dto.location;

public record LocationReqDto(
        String municipality,
        String barangay,
        String province
) {
}
