package ph.dgtech.halalan.voting.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ph.dgtech.halalan.event.VoteCastEvent;

@Configuration
public class ObservationConfig {


    @Bean
    public KafkaTemplate<String, VoteCastEvent> kafkaTemplate(
            ProducerFactory<String, VoteCastEvent> producerFactory,
            ObservationRegistry observationRegistry) {

        KafkaTemplate<String, VoteCastEvent> kafkaTemplate =
                new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setObservationEnabled(true);
        return kafkaTemplate;
    }

    @Bean
    ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }
}
