package ph.dgtech.halalan.voter.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ph.dgtech.halalan.voter.registration.dto.RegistrationRequest;
import ph.dgtech.halalan.voter.registration.service.RegistrationService;

@SpringBootApplication
public class VoterRegistrationApplication  {

    public static void main(String[] args) {
        SpringApplication.run(VoterRegistrationApplication.class, args);
    }

}
