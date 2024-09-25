package ph.dgtech.halalan.polling.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public abstract class ApiResponse {
    private String status;
    private String message;
    private Object data;
    private List<String> errors;

    public ApiResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
