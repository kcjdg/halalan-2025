package ph.dgtech.halalan.ballot.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ph.dgtech.halalan.ballot.dto.CandidateDto;
import ph.dgtech.halalan.ballot.dto.PageResponse;
import ph.dgtech.halalan.ballot.dto.mapper.CandidateMapper;
import ph.dgtech.halalan.ballot.exception.NotFoundException;
import ph.dgtech.halalan.ballot.model.Candidate;
import ph.dgtech.halalan.ballot.repo.CandidateRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    public void createCandidate(CandidateDto request) {
        log.info("Creating candidate: {}", request);
        final var entity = candidateMapper.toEntity(request);
        candidateRepository.save(entity);
    }

    public void updateCandidate(String id, CandidateDto reqDto) {
        var candidateEntity = candidateRepository.findById(id).orElseThrow(() -> new NotFoundException("Candidate not found"));
        candidateMapper.updateCandidateFromDto(reqDto, candidateEntity);
        candidateRepository.save(candidateEntity);
    }


    public PageResponse<CandidateDto> getCandidatesByOfficeTitle(Optional<String> officeTitle, Pageable pageable) {
        Page<Candidate> page;
        if (officeTitle.isPresent()) {
            page = candidateRepository.findByOfficeTitle(officeTitle.get(), pageable);
        } else {
            page = candidateRepository.findAll(pageable);
        }

        var candidateList = candidateMapper.mapToList(page.getContent());
        return new PageResponse<>(candidateList, page.getNumber(), page.getSize(), page.getTotalPages());
    }


}
