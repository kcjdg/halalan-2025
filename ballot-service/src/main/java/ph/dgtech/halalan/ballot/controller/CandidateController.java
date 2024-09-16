package ph.dgtech.halalan.ballot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ph.dgtech.halalan.ballot.dto.CandidateReqDto;
import ph.dgtech.halalan.ballot.model.Candidate;
import ph.dgtech.halalan.ballot.model.enums.CandidateStatus;
import ph.dgtech.halalan.ballot.service.CandidateService;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCandidate(@Valid @RequestBody CandidateReqDto request) {
        candidateService.createCandidate(request);
    }


    //Add list of required fields
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCandidate(@RequestParam String id, @RequestBody @Valid CandidateReqDto request) {
        candidateService.updateCandidate(id, request);
    }


    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void partialCandidateUpdate(@RequestParam String id, @RequestBody @Valid CandidateReqDto request) {
        candidateService.updateCandidate(id, request);
    }




}
