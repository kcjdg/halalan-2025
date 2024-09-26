package ph.dgtech.halalan.ballot.dto.mapper;

import org.mapstruct.*;
import ph.dgtech.halalan.ballot.dto.CandidateDto;
import ph.dgtech.halalan.ballot.model.Candidate;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CandidateMapper {

     Candidate toEntity(CandidateDto request);

     void updateCandidateFromDto(CandidateDto dto, @MappingTarget Candidate entity);

     List<CandidateDto> mapToList (List<Candidate> candidates);

}
