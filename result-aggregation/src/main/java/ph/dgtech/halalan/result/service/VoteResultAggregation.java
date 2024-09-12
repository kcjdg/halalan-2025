package ph.dgtech.halalan.result.service;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ph.dgtech.halalan.event.VoteCastEvent;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.inc;


@Service
@Slf4j
@RequiredArgsConstructor
public class VoteResultAggregation {

    @Autowired
    private MongoClient mongoClient;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Value("${spring.data.mongodb.collection}")
    private String collectionName;

    @KafkaListener(topics = "vote-cast")
    public void listen(VoteCastEvent event) {
        log.info("Received message: {}", event);
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> tallyCollection = database.getCollection(collectionName);
        var list = event.getVotedCandidates()
                .stream()
                .map(vote -> new UpdateOneModel<Document>(and(
                        eq("positionId", vote.getPositionId().toString()),
                        eq("candidateId", vote.getCandidateId().toString())),
                        inc("voteCount", 1), new UpdateOptions().upsert(true)))
                .map(writeModel->(WriteModel<Document>) writeModel)
                .toList();
        tallyCollection.bulkWrite(list);
    }

}
