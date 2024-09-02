package ph.dgtech.halalan.voter.registration.exception;

public class InvalidRequestFormatException extends RuntimeException {
    private Object detail;
    public InvalidRequestFormatException(String message, Object detail) {
        super(message);
        this.detail = detail;
    }

    public Object getDetail() {
        return detail;
    }
}
