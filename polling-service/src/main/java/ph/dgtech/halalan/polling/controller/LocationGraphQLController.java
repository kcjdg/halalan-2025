package ph.dgtech.halalan.polling.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ph.dgtech.halalan.polling.dto.location.BarangayDto;
import ph.dgtech.halalan.polling.dto.location.MunicipalityDto;
import ph.dgtech.halalan.polling.dto.location.ProvinceDto;
import ph.dgtech.halalan.polling.dto.location.RegionDto;
import ph.dgtech.halalan.polling.model.location.Barangay;
import ph.dgtech.halalan.polling.model.location.Municipality;
import ph.dgtech.halalan.polling.service.LocationGraphQLService;

import java.util.List;

@Controller
@Slf4j
public class LocationGraphQLController {

    @Autowired
    private LocationGraphQLService locationService;

    @QueryMapping
    public List<RegionDto> allRegions() {
        return locationService.getAllRegions();
    }

    @QueryMapping
    public RegionDto regionById(@Argument  Long regionId) {
        return locationService.getRegionById(regionId);
    }

    @QueryMapping
    public List<ProvinceDto> provincesByRegion(@Argument Long regionId) {
        return locationService.getProvincesByRegion(regionId);
    }

    @QueryMapping
    public List<MunicipalityDto> municipalitiesByProvince(@Argument Long provinceId) {
        return locationService.getMunicipalitiesByProvince(provinceId);
    }

    @QueryMapping
    public List<BarangayDto> barangaysByMunicipality(@Argument Long municipalityId) {
        return locationService.getBarangaysByMunicipality(municipalityId);
    }
}
