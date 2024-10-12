package ph.dgtech.halalan.voter.profile.service;

import groovy.util.logging.Slf4j;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.dgtech.halalan.event.GenerateBallotEvent;
import ph.dgtech.halalan.voter.profile.dto.info.AddressInfo;

import javax.mail.Address;

@Service
@RequiredArgsConstructor
@Slf4j
public class BallotGenerationService {

    private final KafkaTemplate<String, GenerateBallotEvent> kafkaTemplate;

    @Transactional
    public void sendBallotDetails(String voterId, AddressInfo addressInfo){
        GenerateBallotEvent event = new GenerateBallotEvent();
        event.setElectionId("2025");
        event.setBarangay(addressInfo.barangay());
        event.setMunicipality(addressInfo.municipality());
        event.setProvince(addressInfo.province());
        event.setRegion(addressInfo.region());
        event.setVoterId(voterId);
        kafkaTemplate.send("generate-ballot", event);
    }
}
