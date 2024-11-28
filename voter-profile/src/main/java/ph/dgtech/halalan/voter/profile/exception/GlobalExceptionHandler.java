package ph.dgtech.halalan.voter.profile.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ph.dgtech.halalan.voter.profile.dto.ErrorDto;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String ERROR_LOG_FORMAT = "Error: URI: {}, ErrorCode: {}, Message: {}";

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorDto errorVm = new ErrorDto(HttpStatus.FORBIDDEN.toString(), "Access Denied", message);
        log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), 403, message);
        log.debug(ex.toString());
        return new ResponseEntity<>(errorVm, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorDto> handleUserAlreadyExistsException(UserAlreadyExistException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorDto error = new ErrorDto(HttpStatus.BAD_REQUEST.toString(), "Bad request", message);
        log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), 400, message);
        log.debug(ex.toString());
        return ResponseEntity.badRequest().body(error);
    }


    @ExceptionHandler(InvalidRequestFormatException.class)
    public ResponseEntity<ErrorDto> handlInvalidRequest(InvalidRequestFormatException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorDto error = new ErrorDto(HttpStatus.BAD_REQUEST.toString(), "Bad request", ex.getMessage(), List.of(ex.getDetail()));
        log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), 400, message);
        log.debug(ex.toString());
        return ResponseEntity.badRequest().body(error);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorDto errorVm = new ErrorDto(HttpStatus.NOT_FOUND.toString(), "NotFound", message);
        log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), 404, message);
        log.debug(ex.toString());
        return new ResponseEntity<>(errorVm, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .toList();

        ErrorDto errorVm = new ErrorDto(HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), "Request information is not valid", errors);
        return ResponseEntity.badRequest().body(errorVm);
    }



    @ExceptionHandler(InternalClientException.class)
    public ResponseEntity<ErrorDto> internalClient(InternalClientException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorDto errorVm = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(), List.of(ex.getDetail()));
        log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), 500, message);
        log.debug(ex.toString());
        return new ResponseEntity<>(errorVm, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNoResourceFound(NoResourceFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The requested resource could not be found.");
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorDto> handleOtherException(Exception ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorDto errorVm = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "");
        log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), 500, message);
        log.debug(ex.toString());
        return new ResponseEntity<>(errorVm, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private String getServletPath(WebRequest webRequest) {
        ServletWebRequest servletRequest = (ServletWebRequest) webRequest;
        return servletRequest.getRequest().getServletPath();
    }
}
