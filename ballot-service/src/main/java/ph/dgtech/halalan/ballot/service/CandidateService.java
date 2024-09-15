package ph.dgtech.halalan.ballot.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ph.dgtech.halalan.ballot.dto.CandidateReqDto;
import ph.dgtech.halalan.ballot.dto.mapper.CandidateMapper;
import ph.dgtech.halalan.ballot.repo.CandidateRepository;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    public void createCandidate(CandidateReqDto request) {
        final var entity = candidateMapper.toEntity(request);
        candidateRepository.save(entity);
    }
}
