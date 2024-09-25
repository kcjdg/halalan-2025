package ph.dgtech.halalan.polling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ph.dgtech.halalan.polling.dto.AddressDetails;
import ph.dgtech.halalan.polling.dto.AddressRequest;
import ph.dgtech.halalan.polling.repository.location.AddressValidationRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressValidationService {

    private final AddressValidationRepository addressValidationRepository;

    public Optional<AddressDetails> validateAddress(AddressRequest request) {
        var resp = addressValidationRepository.getLocationDetails(
                request.region(),
                request.province(),
                request.municipality(),
                request.barangay()).getFirst();
        return Optional.ofNullable(
                new AddressDetails((String)resp[0], (String)resp[1], (String)resp[2], (String)resp[3])
             );
    }
}