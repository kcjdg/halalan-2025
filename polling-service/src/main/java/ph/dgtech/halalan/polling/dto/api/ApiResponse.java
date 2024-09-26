package ph.dgtech.halalan.polling.dto.api;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ApiResponse {
    protected String status;
    protected String message;
    protected Object data;
    protected List<String> errors;

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
