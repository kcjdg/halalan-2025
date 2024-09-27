package ph.dgtech.halalan.voter.profile.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import ph.dgtech.halalan.voter.profile.dto.info.AddressInfo;

public interface AddressClient {
    Logger log = LoggerFactory.getLogger(AddressClient.class);


    @PostExchange("/address/validate")
    @CircuitBreaker(name = "addressValidation", fallbackMethod = "fallBackMethod")
    @Retry(name = "addressValidation")
    ResponseEntity<?> validateAddress(@RequestBody AddressInfo request);

    default ResponseEntity<?> fallBackMethod(String userId, AddressInfo details, Throwable throwable) {
        log.info("Cannot validate user {} with address of {} failure reason {}", userId, details, throwable.getMessage());
        return null;
    }
}
