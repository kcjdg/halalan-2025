package ph.dgtech.halalan.polling.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.dgtech.halalan.polling.dto.location.ProvinceDto;
import ph.dgtech.halalan.polling.dto.location.RegionDto;
import ph.dgtech.halalan.polling.dto.mapper.LocMapper;
import ph.dgtech.halalan.polling.model.location.Barangay;
import ph.dgtech.halalan.polling.model.location.Municipality;
import ph.dgtech.halalan.polling.repository.location.BarangayRepository;
import ph.dgtech.halalan.polling.repository.location.MunicipalityRepository;
import ph.dgtech.halalan.polling.repository.location.ProvinceRepository;
import ph.dgtech.halalan.polling.repository.location.RegionRepository;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationGraphQLService {

    private final RegionRepository regionRepository;
    private final ProvinceRepository provinceRepository;
    private final MunicipalityRepository municipalityRepository;
    private final BarangayRepository barangayRepository;
    private final LocMapper locMapper;


    public List<RegionDto> getAllRegions() {
        return locMapper.toRegionDto(regionRepository.findAll());
    }

    public RegionDto getRegionById(Long regionId) {
        var regionList = regionRepository
                .findById(regionId)
                .orElseThrow(NotFoundException::new);
        return locMapper.toRegionDto(regionList);
    }

    public List<ProvinceDto> getProvincesByRegion(Long regionId) {
        var provinceList = provinceRepository
                .findAllByRegionId(regionId);
        return locMapper.toProvinceDto(provinceList);
    }


    public List<Municipality> getMunicipalitiesByProvince(Long provinceId) {
        return municipalityRepository
                .findAllByProvinceId(provinceId);
    }

    public List<Barangay> getBarangaysByMunicipality(Long municipalityId) {
        return barangayRepository
                .findByMunicipalityId(municipalityId);
    }


}

