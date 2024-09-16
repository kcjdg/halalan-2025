package ph.dgtech.halalan.ballot.dto.mapper;

import org.mapstruct.*;
import ph.dgtech.halalan.ballot.dto.CandidateReqDto;
import ph.dgtech.halalan.ballot.model.Candidate;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CandidateMapper {

     Candidate toEntity(CandidateReqDto request);

     void updateCandidateFromDto(CandidateReqDto dto, @MappingTarget Candidate entity);


}
