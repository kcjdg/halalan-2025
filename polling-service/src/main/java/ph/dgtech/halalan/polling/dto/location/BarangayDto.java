package ph.dgtech.halalan.polling.dto.location;

public record BarangayDto (
        Long barangayId,
        String barangayName,
        MunicipalityDto municipality){
}
