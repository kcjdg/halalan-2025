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

//    @Autowired
//    private ProfileService profileService;
//    @Override
//    public void run(String... args) throws Exception {
//        profileService.updateVoter("e67d8a69-add3-4d13-82a9-b940c60310fb", VoterRequestDetails.builder().firstName("John").lastName("Doe").dob(LocalDate.of(1990, 1, 1)).build());
//        profileService.updateVoter("", VoterRequestDetails.builder().firstName("John").lastName("Doe").dob(LocalDate.of(1990, 1, 1)).build());
//    }
}
