package ph.dgtech.halalan.polling.dto;

public record LocationReqDto(
        String municipality,
        String barangay,
        String province
) {
}
