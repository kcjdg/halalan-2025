package ph.dgtech.halalan.voter.profile.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ph.dgtech.halalan.voter.profile.dto.VoterResponseDetails;
import ph.dgtech.halalan.voter.profile.dto.VoterRequestDetails;
import ph.dgtech.halalan.voter.profile.service.ProfileService;

@RestController
@RequestMapping("/ph/voter/")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public VoterResponseDetails register(@Valid @RequestBody VoterRequestDetails voterRequestDetails) {
        log.info("registration request received: {}", voterRequestDetails);
        return profileService.registerVoter(voterRequestDetails);
    }


    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String userId, @RequestBody  @Valid VoterRequestDetails voterRequestDetails) {
        log.info("update request received: {}", voterRequestDetails);
        profileService.updateVoter(userId, voterRequestDetails);
    }


}
