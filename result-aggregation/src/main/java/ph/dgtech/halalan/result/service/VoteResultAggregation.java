package ph.dgtech.halalan.result.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ph.dgtech.halalan.event.VoteCastEvent;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoteResultAggregation {

    @KafkaListener(topics = "vote-cast")
    public void listen(VoteCastEvent event) {
        log.info("Received message: {}", event);
    }

}
