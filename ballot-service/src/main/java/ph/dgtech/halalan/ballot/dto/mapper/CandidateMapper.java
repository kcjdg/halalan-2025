package ph.dgtech.halalan.ballot.dto.mapper;

import org.mapstruct.Mapper;
import ph.dgtech.halalan.ballot.dto.CandidateReqDto;
import ph.dgtech.halalan.ballot.model.Candidate;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    public Candidate toEntity(CandidateReqDto request);

}
