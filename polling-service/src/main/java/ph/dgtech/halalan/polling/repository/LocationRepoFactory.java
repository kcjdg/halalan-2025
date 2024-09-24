//package ph.dgtech.halalan.polling.repository;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import ph.dgtech.halalan.polling.repository.location.BarangayRepository;
//import ph.dgtech.halalan.polling.repository.location.MunicipalityRepository;
//import ph.dgtech.halalan.polling.repository.location.ProvinceRepository;
//import ph.dgtech.halalan.polling.repository.location.RegionRepository;
//
////@Component
//@RequiredArgsConstructor
//public class LocationRepoFactory {
//    private final RegionRepository regionRepository;
//    private final ProvinceRepository provinceRepository;
//    private final MunicipalityRepository municipalityRepository;
//    private final BarangayRepository barangayRepository;
//
//    public <T> T getRepository(Class<T> repositoryClass) {
//        return switch (repositoryClass.getSimpleName()) {
//            case "RegionRepository" -> (T) regionRepository;
//            case "ProvinceRepository" -> (T) provinceRepository;
//            case "MunicipalityRepository" -> (T) municipalityRepository;
//            case "BarangayRepository" -> (T) barangayRepository;
//            default -> throw new IllegalArgumentException("Unknown repository: " + repositoryClass);
//        };
//    }
//}
