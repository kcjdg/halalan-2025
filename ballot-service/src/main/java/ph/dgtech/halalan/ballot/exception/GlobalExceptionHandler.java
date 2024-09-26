package ph.dgtech.halalan.ballot.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import ph.dgtech.halalan.ballot.dto.ErrorDto;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String ERROR_LOG_FORMAT = "Error: URI: {}, ErrorCode: {}, Message: {}";

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorDto<String> errorVm = new ErrorDto<>(HttpStatus.NOT_FOUND.toString(), "NotFound", message);
        log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), 404, message);
        log.debug(ex.toString());
        return new ResponseEntity<>(errorVm, HttpStatus.NOT_FOUND);
    }

    private String getServletPath(WebRequest webRequest) {
        ServletWebRequest servletRequest = (ServletWebRequest) webRequest;
        return servletRequest.getRequest().getServletPath();
    }

}
