package ph.dgtech.halalan.polling.dto;

import java.util.List;

public class ErrorResponse extends ApiResponse{

    public ErrorResponse(String message, List<String> errors) {
        super("failure", message, errors);
    }

}

