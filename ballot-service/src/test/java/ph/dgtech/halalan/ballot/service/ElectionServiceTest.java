package ph.dgtech.halalan.ballot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ph.dgtech.halalan.ballot.dto.ElectionDto;
import ph.dgtech.halalan.ballot.exception.NotFoundException;
import ph.dgtech.halalan.ballot.model.primary.Election;
import ph.dgtech.halalan.ballot.repo.primary.ElectionRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
class ElectionServiceTest {


    @Mock
    private ElectionRepository electionRepository;

    @InjectMocks
    private ElectionService electionService;

    private Election election;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        election = new Election();
        election.setElectionId("3930ab8e-de67-11ef-b28f-0242ac13000b");
        election.setElectionType("National");
        election.setElectionName("Halalan 2025");
        election.setElectionDate(LocalDate.of(2025, 5, 5));
        election.setCreatedBy("admin");
        election.setCreatedAt(LocalDate.of(2025, 1, 1).atStartOfDay());
        election.setFlag(true);
    }


    @Test
    void testSaveElection_whenValid_shouldSuccess() {
        when(electionRepository.save(any(Election.class))).thenReturn(election);
        Election savedElection = electionService.saveElection(new ElectionDto(
                election.getElectionName(),
                election.getElectionDate(),
                election.getElectionType()
        ));

        assertNotNull(savedElection);
        assertEquals("Halalan 2025", savedElection.getElectionName());
        verify(electionRepository, times(1)).save(any(Election.class));
    }


    @Test
    void testGetLastActiveElection_whenPresent_returnElectionDto(){
        when(electionRepository.findByFlagOrderByElectionDateAsc(true)).thenReturn(Optional.of(election));
        ElectionDto  dto = electionService.getLastActiveElection();
        assertNotNull(dto);
        assertEquals("3930ab8e-de67-11ef-b28f-0242ac13000b", dto.electionId());
    }


    @Test
    void testGetLastActiveElection_whenNoActiveElection_thenThrowNotFound(){
        assertThrows(NotFoundException.class, () -> {
            when(electionRepository.findByFlagOrderByElectionDateAsc(true)).thenThrow(NotFoundException.class);
            electionService.getLastActiveElection();
        });
    }




}
