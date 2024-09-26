package ph.dgtech.halalan.polling.dto.api;

import java.util.List;

public class ErrorResponse extends ApiResponse{

    public ErrorResponse(String message, List<String> errors) {
        super("Failure", message);
        this.errors = errors;
    }

}

