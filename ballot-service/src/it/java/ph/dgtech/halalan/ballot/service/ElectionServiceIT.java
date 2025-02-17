package ph.dgtech.halalan.ballot.service;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ph.dgtech.halalan.ballot.config.IntegrationTestConfiguration;
import ph.dgtech.halalan.ballot.dto.ElectionDto;
import ph.dgtech.halalan.ballot.exception.NotFoundException;
import ph.dgtech.halalan.ballot.model.primary.Election;
import ph.dgtech.halalan.ballot.repo.primary.ElectionRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class ElectionServiceIT extends IntegrationTestConfiguration {

    @Autowired
    private ElectionService electionService;

    @Autowired
    private ElectionRepository electionRepository;

    private ElectionDto electionDto;

    @BeforeEach
    void setup() {
        electionDto = new ElectionDto(
                "Halalan 2025",
                LocalDate.of(2025, 5, 5),
                "National"
        );

    }

    @AfterEach
    void tearDown() {
        electionRepository.deleteAll();
    }


    @DisplayName("Save election with mandatory fields should success")
    @Test
    void testSaveElection_whenValid_shouldSuccess() {
        Election savedElection = electionService.saveElection(electionDto);
        assertNotNull(savedElection);
        assertNotNull(savedElection.getElectionId());
        assertEquals("Halalan 2025", savedElection.getElectionName());
    }


    @DisplayName("Get last active election when empty record then should throw not found")
    @Test
    void testGetLastActiveElection_whenEmptyRecord_thenThrowNotFoundException() {
        electionDto = new ElectionDto(
                "Halalan 2025 ",
                LocalDate.of(2025, 5, 5),
                "National",
                false
        );
        electionService.saveElection(electionDto);
        assertThrows(NotFoundException.class, () -> {
            electionService.getLastActiveElection();
        });
    }

    @DisplayName("Get last active election when multiple records are present")
    @Test
    void testGetLastActiveElection_whenMultipleRecordIsPresent_returnLastActive() {
        electionService.saveElection(electionDto);
        electionDto = new ElectionDto(
                "Presidential Election 2028",
                LocalDate.of(2028, 5, 5),
                "National",
                false
        );
        //inactive
        electionService.saveElection(electionDto);

        var list = electionRepository.findAll();
        assertEquals(2, list.size());

        electionDto = electionService.getLastActiveElection();
        assertNotNull(electionDto);
        assertEquals("Halalan 2025", electionDto.electionName());
    }


}