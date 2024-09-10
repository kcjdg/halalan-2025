package ph.dgtech.halalan.voting.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ph.dgtech.halalan.voting.dto.VoteRequestDto;
import ph.dgtech.halalan.voting.dto.mappers.VoteCastMapper;
import ph.dgtech.halalan.voting.model.CandidatePosition;
import ph.dgtech.halalan.voting.repository.ElectionVoteRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CastVotingService {
    private final ElectionVoteRepository electionVoteRepository;
    private final VoteCastMapper mapper;

    public void castVote(VoteRequestDto voteRequest) {
        var electionVote = mapper.mapToElectionVote(voteRequest);
        electionVoteRepository.save(electionVote);
    }

}
