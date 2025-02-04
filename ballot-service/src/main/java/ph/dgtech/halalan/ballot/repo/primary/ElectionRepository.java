package ph.dgtech.halalan.ballot.repo.primary;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ph.dgtech.halalan.ballot.model.primary.Election;

import java.util.List;
import java.util.Optional;


@Repository
public interface ElectionRepository extends JpaRepository<Election, String> {

    @Query("select e from Election e where e.flag = ?1 order by e.electionDate desc")
    Optional<Election> findByFlagOrderByElectionDateAsc(boolean flag);
}
