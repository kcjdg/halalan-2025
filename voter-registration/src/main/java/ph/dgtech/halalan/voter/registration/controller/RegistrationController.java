package ph.dgtech.halalan.voter.registration.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ph.dgtech.halalan.voter.registration.dto.RegistrationRequest;
import ph.dgtech.halalan.voter.registration.dto.RegistrationResponse;
import ph.dgtech.halalan.voter.registration.service.RegistrationService;

@RestController
@RequestMapping("/ph/voter/")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse register(@RequestBody RegistrationRequest voterRegistrationRequest) {
        log.info("request received: {}", voterRegistrationRequest);
        return registrationService.registerVoter(voterRegistrationRequest);
    }


}
