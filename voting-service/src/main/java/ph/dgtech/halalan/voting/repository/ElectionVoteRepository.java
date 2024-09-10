package ph.dgtech.halalan.voting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ph.dgtech.halalan.voting.model.ElectionVote;

public interface ElectionVoteRepository extends MongoRepository<ElectionVote, String> {
}
