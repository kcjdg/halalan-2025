package ph.dgtech.halalan.voter.profile.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ph.dgtech.halalan.voter.profile.dto.ProfileQueryResponseDetails;
import ph.dgtech.halalan.voter.profile.dto.ProfileUpdateRequestDetails;
import ph.dgtech.halalan.voter.profile.dto.RegistrationRequestDetails;
import ph.dgtech.halalan.voter.profile.dto.RegistrationResponseDetails;
import ph.dgtech.halalan.voter.profile.service.ProfileService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponseDetails register(@Valid @RequestBody RegistrationRequestDetails request) {
        log.info("registration request received: {}", request);
        return profileService.registerVoter(request);
    }


    @PutMapping("/account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid ProfileUpdateRequestDetails request) {
        log.info("update request received: {}", request);
        profileService.updateVoter(request);
    }


    @GetMapping("/account")
    public ProfileQueryResponseDetails getVoterProfile() {
        return profileService.getVoterProfile();
    }


}
