package ph.dgtech.halalan.polling.dto.api;

import ph.dgtech.halalan.polling.dto.address.AddressDetails;

public class SuccessResponse extends ApiResponse{

    public SuccessResponse(String message, AddressDetails data) {
        super("Success", message);
        this.data = data;
    }

}

