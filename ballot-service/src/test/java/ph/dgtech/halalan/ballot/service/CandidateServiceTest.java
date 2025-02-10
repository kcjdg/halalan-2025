package ph.dgtech.halalan.ballot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ph.dgtech.halalan.ballot.dto.CandidateDto;
import ph.dgtech.halalan.ballot.dto.mapper.CandidateMapper;
import ph.dgtech.halalan.ballot.model.primary.Candidate;
import ph.dgtech.halalan.ballot.repo.primary.CandidateRepository;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.*;

class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private CandidateMapper candidateMapper;

    @InjectMocks
    private CandidateService candidateService;

    private CandidateDto candidateDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        candidateDto  = new CandidateDto(
                UUID.randomUUID().toString(),
                "Juan",
                "Dela",
                "Cruz",
                LocalDate.of(1980, 5, 15),
                "Male",
                "https://example.com/photo.jpg",
                UUID.randomUUID(), // electionId
                "President",
                "Independent",
                1,
                "https://juanforpresident.com",
                "A better future for all",
                "Universal healthcare, Education reform, Economic growth",
                "District 1",
                "Region X",
                "juan@example.com",
                "+639171234567",
                "Active",
                50000,
                LocalDate.of(2025, 1, 10),
                "admin",
                "editor",
                "Prominent candidate with strong grassroots support"
        );
    }

    @Test
    void testCreateCandidate_whenValid_shouldSuccess(){
        Candidate candidateEnt = new Candidate();
        when(candidateMapper.toEntity(candidateDto)).thenReturn(candidateEnt);
        when(candidateRepository.save(candidateEnt)).thenReturn(candidateEnt);

        candidateService.createCandidate(candidateDto);
        verify(candidateMapper, times(1)).toEntity(candidateDto);
        verify(candidateRepository, times(1)).save(candidateEnt);
    }


}
