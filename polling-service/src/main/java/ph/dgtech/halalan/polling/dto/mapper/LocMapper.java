package ph.dgtech.halalan.polling.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ph.dgtech.halalan.polling.dto.location.MunicipalityDto;
import ph.dgtech.halalan.polling.dto.location.ProvinceDto;
import ph.dgtech.halalan.polling.dto.location.RegionDto;
import ph.dgtech.halalan.polling.model.location.Municipality;
import ph.dgtech.halalan.polling.model.location.Province;
import ph.dgtech.halalan.polling.model.location.Region;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocMapper {

    @Mapping(target = "regionId", source = "id")
    @Mapping(target = "regionName", source = "name")
    @Mapping(target = "regionDescription", source = "description")
    RegionDto toRegionDto(Region region);

    List<RegionDto> toRegionDto(List<Region> region);


    @Mapping(target = "provinceId", source = "id")
    @Mapping(target = "provinceName", source = "name")
    @Mapping(target = "region", source = "region")  // This maps the Region to RegionDto using the method above
    ProvinceDto toProvinceDto(Province province);

    List<ProvinceDto> toProvinceDto(List<Province> province);



    @Mapping(target = "municipalityId", source = "id")
    @Mapping(target = "municipalityName", source = "name")
    @Mapping(target = "province", source = "province")
    MunicipalityDto toMunicipalityDto(Municipality municipality);

    List<MunicipalityDto> toMunicipalityDto(List<Municipality> municipalities);

}
