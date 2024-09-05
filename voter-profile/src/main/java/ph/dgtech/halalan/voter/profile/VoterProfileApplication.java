package ph.dgtech.halalan.voter.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ph.dgtech.halalan.voter.profile.dto.VoterRequestDetails;
import ph.dgtech.halalan.voter.profile.service.ProfileService;

import java.time.LocalDate;

@SpringBootApplication
public class VoterProfileApplication  {

    public static void main(String[] args) {
        SpringApplication.run(VoterProfileApplication.class, args);
    }


}
