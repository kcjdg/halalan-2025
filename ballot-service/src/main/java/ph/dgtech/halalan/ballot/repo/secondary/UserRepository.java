package ph.dgtech.halalan.ballot.repo.secondary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ph.dgtech.halalan.ballot.model.secondary.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.attributes WHERE u.id = :userId")
    Optional<User> findUserWithAttributes(@Param("userId") String userId);
}
