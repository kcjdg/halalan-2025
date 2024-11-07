package ph.dgtech.halalan.ballot.repo.primary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.dgtech.halalan.ballot.model.primary.Candidate;



@Repository
public interface CandidateRepository extends JpaRepository<Candidate, String> {

    Page<Candidate> findByOfficeTitle(String officeTitle, Pageable pageable);
}
