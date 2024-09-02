package ph.dgtech.halalan.voter.registration.exception;

import ph.dgtech.halalan.voter.registration.dto.ErrorDto;

import java.util.List;

public class InvalidFormatException extends RuntimeException {
    private ErrorDto.Field field;

    public InvalidFormatException(String message) {
        super(message);
    }

    public InvalidFormatException(String message, ErrorDto.Field field) {
        super(message);
        this.field = field;
    }

    public List<ErrorDto.Field.Error> getFieldError() {
        return field.errors();
    }
}
