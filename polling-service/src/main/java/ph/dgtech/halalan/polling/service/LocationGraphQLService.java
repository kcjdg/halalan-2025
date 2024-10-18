package ph.dgtech.halalan.polling.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ph.dgtech.halalan.polling.dto.location.BarangayDto;
import ph.dgtech.halalan.polling.dto.location.MunicipalityDto;
import ph.dgtech.halalan.polling.dto.location.ProvinceDto;
import ph.dgtech.halalan.polling.dto.location.RegionDto;
import ph.dgtech.halalan.polling.dto.mapper.LocMapper;
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
        var region = regionRepository
                .findById(regionId)
                .orElseThrow(NotFoundException::new);
        return locMapper.toRegionDto(region);
    }

    public List<ProvinceDto> getProvincesByRegion(Long regionId) {
        var provinceList = provinceRepository
                .findAllByRegionId(regionId);
        return locMapper.toProvinceDto(provinceList);
    }


    public List<MunicipalityDto> getMunicipalitiesByProvince(Long provinceId) {
        var municipalityList =  municipalityRepository
                .findAllByProvinceId(provinceId);
        return locMapper.toMunicipalityDto(municipalityList);
    }


    public List<BarangayDto> getBarangaysByMunicipality(Long municipalityId) {
        var barangayList =  barangayRepository
                .findByMunicipalityId(municipalityId);
        return locMapper.toBarangayDto(barangayList);
    }


}

