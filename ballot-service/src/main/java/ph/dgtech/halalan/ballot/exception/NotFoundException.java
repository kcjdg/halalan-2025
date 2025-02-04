package ph.dgtech.halalan.ballot.exception;


import ph.dgtech.halalan.ballot.utils.MessageUtils;


public class NotFoundException extends RuntimeException {
    private final String message;

    public NotFoundException(){
       this.message = "Unable to found the resource" ;
    }

    public NotFoundException(String errorCode, Object... var2) {
        this.message = MessageUtils.getMessage(errorCode, var2);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
