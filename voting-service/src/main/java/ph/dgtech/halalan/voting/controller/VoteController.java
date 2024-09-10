package ph.dgtech.halalan.voting.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ph.dgtech.halalan.voting.dto.VoteRequestDto;
import ph.dgtech.halalan.voting.service.CastVotingService;

@RestController
@RequiredArgsConstructor
public class VoteController {


    private final CastVotingService castVotingService;
    //TODO: vote
    //TODO: vote-status/{voterId}

    @PostMapping("/vote")
    @ResponseStatus(HttpStatus.CREATED)
    public void castVote(@Valid @RequestBody VoteRequestDto request) {
        castVotingService.castVote(request);
    }

}
