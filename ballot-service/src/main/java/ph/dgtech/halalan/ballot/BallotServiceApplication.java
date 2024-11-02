package ph.dgtech.halalan.ballot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ph.dgtech.halalan.ballot.model.secondary.User;
import ph.dgtech.halalan.ballot.repo.secondary.UserRepository;
import ph.dgtech.halalan.ballot.service.UserService;

import java.util.Optional;

@SpringBootApplication
public class BallotServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BallotServiceApplication.class, args);
    }
}


