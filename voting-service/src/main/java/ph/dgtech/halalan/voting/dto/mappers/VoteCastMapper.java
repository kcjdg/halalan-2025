package ph.dgtech.halalan.voting.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ph.dgtech.halalan.voting.dto.VoteRequestDto;
import ph.dgtech.halalan.voting.model.ElectionVote;

@Mapper(componentModel = "spring")
public interface VoteCastMapper {

    @Mapping(target = ".", source = "votedCandidates")
    ElectionVote mapToElectionVote(VoteRequestDto voteRequestDto);
}
