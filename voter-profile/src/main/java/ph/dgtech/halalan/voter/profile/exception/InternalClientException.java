package ph.dgtech.halalan.voter.profile.exception;


import ph.dgtech.halalan.voter.profile.utils.MessagesUtils;

public class InternalClientException extends RuntimeException {
    private Object detail;
    public InternalClientException(String message, Object detail) {
        super(message);
        this.detail = detail;
    }

    public Object getDetail() {
        return detail;
    }
}
