package ph.dgtech.halalan.ballot.service;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ph.dgtech.halalan.ballot.config.IntegrationTestConfiguration;
import ph.dgtech.halalan.ballot.dto.CandidateDto;
import ph.dgtech.halalan.ballot.model.primary.Candidate;
import ph.dgtech.halalan.ballot.repo.primary.CandidateRepository;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CandidateServiceIT extends IntegrationTestConfiguration {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private Flyway flyway;


    private CandidateDto candidateDto;

    @BeforeEach
    void setup(){
         candidateDto = new CandidateDto(
                null,  // candidateId is READ_ONLY, usually assigned by the system
                "Juan",
                "Dela",
                "Cruz",
                LocalDate.of(1980, 5, 15),
                "Male",
                "https://example.com/photos/juan_cruz.jpg",
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                "Mayor", "People's Reform Party",
                7,
                "https://juancruz2025.com",
                "For a Better Future",
                "Education Reform, Healthcare Improvement, Economic Growth",
                "District 1",
                "Metro City",
                "juan.cruz@example.com",
                "+639171234567",
                "QUALIFIED",
                12500,
                LocalDate.of(2024, 10, 1),
                "admin",
                "editor",
                "Strong supporter of social reforms."
        );
    }
    @AfterEach
    void tearDown() {
        candidateRepository.deleteAll();
    }


    @DisplayName("Save candidate with mandatory fields should success")
    @Test
    void testSaveCandidate_whenValid_shouldSuccess() {
        Candidate candidate = candidateService.createCandidate(candidateDto);
        assertNotNull(candidate);
        assertNotNull(candidate.getCandidateId());
        assertEquals("Juan", candidate.getFirstName());
    }

}
