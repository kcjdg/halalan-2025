package ph.dgtech.halalan.ballot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ph.dgtech.halalan.ballot.dto.ElectionDto;
import ph.dgtech.halalan.ballot.model.primary.Election;
import ph.dgtech.halalan.ballot.repo.primary.ElectionRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElectionService {

    private final ElectionRepository electionRepository;

    public void saveElection(ElectionDto electionDto){
        Election election = new Election();
        election.setElectionDate(electionDto.electionDate());
        election.setElectionName(electionDto.electionName());
        election.setElectionType(electionDto.electionType());
        election.setCreatedBy("admin");
        electionRepository.save(election);
    }


    public ElectionDto getLastActiveElection(){
        Election election = electionRepository
                .findAll().getFirst();
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
