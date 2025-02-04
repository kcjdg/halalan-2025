package ph.dgtech.halalan.ballot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ph.dgtech.halalan.ballot.dto.ElectionDto;
import ph.dgtech.halalan.ballot.exception.NotFoundException;
import ph.dgtech.halalan.ballot.model.primary.Election;
import ph.dgtech.halalan.ballot.repo.primary.ElectionRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElectionService {

    private final ElectionRepository electionRepository;

    public Election saveElection(ElectionDto electionDto){
        Election election = new Election();
        election.setElectionDate(electionDto.electionDate());
        election.setElectionName(electionDto.electionName());
        election.setElectionType(electionDto.electionType());
        election.setCreatedBy("admin");
        return electionRepository.save(election);
    }


    public ElectionDto getLastActiveElection(){
        Election election = electionRepository
                .findByFlagOrderByElectionDateAsc(true)
                .orElseThrow(NotFoundException::new);
        return new ElectionDto(
                election.getElectionId(),
                election.getElectionName(),
                election.getElectionDate(),
                election.getElectionType(),
                election.getCreatedAt(),
                election.getCreatedBy(),
                election.getUpdatedBy(),
                election.getUpdatedTimestamp(),
                election.isFlag()
        );
    }


}
