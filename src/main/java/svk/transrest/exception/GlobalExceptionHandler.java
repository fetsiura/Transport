package svk.transrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import svk.transrest.payload.response.ResponseError;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseError> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                         WebRequest webRequest) {
        ResponseError error = new ResponseError(HttpStatus.NOT_FOUND, exception.getMessage(), LocalDateTime.now().toString());
        webRequest.getDescription(false);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PhotoNotBelongException.class)
    public ResponseEntity<ResponseError> handlePhotoNotBelongException(PhotoNotBelongException exception,
                                                                       WebRequest webRequest) {
        ResponseError error = new ResponseError(HttpStatus.NOT_ACCEPTABLE, exception.getMessage(), LocalDateTime.now().toString());
        webRequest.getDescription(false);
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(TransAPIException.class)
    public ResponseEntity<ResponseError> handleTransAPIException(TransAPIException exception,
                                                                 WebRequest webRequest) {
        ResponseError error = new ResponseError(exception.getHttpStatus(), exception.getMessage(), LocalDateTime.now().toString());
        webRequest.getDescription(false);
        return new ResponseEntity<>(error, exception.getHttpStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseError> handleBadCredentialsException(WebRequest webRequest) {
        ResponseError error = new ResponseError(HttpStatus.UNAUTHORIZED, "Username email or password not correct", LocalDateTime.now().toString());
        webRequest.getDescription(false);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    //global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleGlobalException(Exception exception,
                                                               WebRequest webRequest) {
        ResponseError error = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), LocalDateTime.now().toString());
        webRequest.getDescription(false);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
