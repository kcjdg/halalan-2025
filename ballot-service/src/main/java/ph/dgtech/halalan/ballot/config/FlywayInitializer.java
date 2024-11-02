package ph.dgtech.halalan.ballot.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class FlywayInitializer {

    @Autowired
    private List<Flyway> flyways;

    @PostConstruct
    public void migrateFlyway() {
        flyways.forEach(Flyway::migrate);
    }
}
