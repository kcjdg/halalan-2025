package ph.dgtech.halalan.polling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.dgtech.halalan.polling.dto.address.AddressDetails;
import ph.dgtech.halalan.polling.dto.address.AddressRequest;
import ph.dgtech.halalan.polling.repository.location.AddressValidationRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressValidationService {

    private final AddressValidationRepository addressValidationRepository;

    @Transactional
    public Optional<AddressDetails> validateAddress(AddressRequest request) {
        return Optional.ofNullable(addressValidationRepository.getLocationDetails(
                request.region(),
                request.province(),
                request.municipality(),
                request.barangay()));
    }
}