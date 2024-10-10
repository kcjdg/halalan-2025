package ph.dgtech.halalan.ballot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ph.dgtech.halalan.ballot.dto.CandidateDto;
import ph.dgtech.halalan.ballot.dto.PageResponse;
import ph.dgtech.halalan.ballot.service.CandidateService;

import java.util.Optional;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCandidate(@Valid @RequestBody CandidateDto request) {
        candidateService.createCandidate(request);
    }

    //Add list of required fields
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCandidate(@RequestParam String id, @RequestBody @Valid CandidateDto request) {
        candidateService.updateCandidate(id, request);
    }


    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void partialCandidateUpdate(@RequestParam String id, @RequestBody @Valid CandidateDto request) {
        candidateService.updateCandidate(id, request);
    }


    @GetMapping
    public PageResponse<CandidateDto> getListOfCandidates(@RequestParam(required = false) String officeTitle,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(officeTitle));
        return candidateService.getCandidatesByOfficeTitle(Optional.ofNullable(officeTitle), pageable);
    }

}
