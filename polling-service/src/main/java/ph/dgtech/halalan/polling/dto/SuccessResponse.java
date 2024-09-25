package ph.dgtech.halalan.polling.dto;

public class SuccessResponse extends ApiResponse{

    public SuccessResponse(String message, AddressDetails data) {
        super("success", message, data);
    }

}

