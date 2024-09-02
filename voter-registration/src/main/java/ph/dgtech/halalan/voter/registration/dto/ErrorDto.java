package ph.dgtech.halalan.voter.registration.dto;

import java.util.ArrayList;
import java.util.List;

public record ErrorDto(String statusCode, String title, String detail, List<Field.Error> error) {
    public ErrorDto(String statusCode, String title, String detail) {
        this(statusCode, title, detail, new ArrayList<>());
    }

    public record Field(List<Error> errors) {
        public record Error(String field, String errorMessage, List<String> params) {

        }
    }

}
