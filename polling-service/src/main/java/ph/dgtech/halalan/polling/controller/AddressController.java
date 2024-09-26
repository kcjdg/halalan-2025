package ph.dgtech.halalan.polling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.dgtech.halalan.polling.dto.address.AddressRequest;
import ph.dgtech.halalan.polling.dto.api.ApiResponse;
import ph.dgtech.halalan.polling.dto.api.ErrorResponse;
import ph.dgtech.halalan.polling.dto.api.SuccessResponse;
import ph.dgtech.halalan.polling.service.AddressValidationService;

import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressValidationService addressValidationService;

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse> validateAddress(@RequestBody AddressRequest request) {
        var resp = addressValidationService.validateAddress(request);
        return resp.
                <ResponseEntity<ApiResponse>>map(addressDetails -> ResponseEntity.ok(new SuccessResponse("Address found", addressDetails)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Address not found", List.of(
                        "Combination of address is not valid",
                        "Request: " + request))));
    }

}
