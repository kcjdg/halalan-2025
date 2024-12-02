package ph.dgtech.halalan.voting.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.dgtech.halalan.event.VoteCastEvent;
import ph.dgtech.halalan.voting.dto.VoteRequestDto;
import ph.dgtech.halalan.voting.dto.mappers.VoteCastMapper;
import ph.dgtech.halalan.voting.repository.ElectionVoteRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CastVotingService {
    private final ElectionVoteRepository electionVoteRepository;
    private final VoteCastMapper mapper;
    private final KafkaTemplate<String, VoteCastEvent> kafkaTemplate;


    @Transactional
    public void castVote(String voterId, VoteRequestDto voteRequest) {
        var electionVote = mapper.mapToElectionVote(voteRequest);
        electionVote.setVoterId(voterId);
        var saveData = electionVoteRepository.save(electionVote);
        //TODO: send the message to kafka
        log.info("sending votes to kafka %s %s".formatted(voterId, voteRequest));
        var voteCastEvent = mapper.mapToVoteCastEvent(saveData);
        kafkaTemplate.send("vote-cast", voteCastEvent);
    }

}

