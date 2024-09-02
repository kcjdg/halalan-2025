package ph.dgtech.halalan.voter.profile.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ph.dgtech.halalan.voter.profile.dto.RegistrationRequest;
import ph.dgtech.halalan.voter.profile.dto.RegistrationResponse;
import ph.dgtech.halalan.voter.profile.service.RegistrationService;

@RestController
@RequestMapping("/ph/voter/")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse register(@Valid @RequestBody RegistrationRequest voterRegistrationRequest) {
        log.info("request received: {}", voterRegistrationRequest);
        return registrationService.registerVoter(voterRegistrationRequest);
    }


}
