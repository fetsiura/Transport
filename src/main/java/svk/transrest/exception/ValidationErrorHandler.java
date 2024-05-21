package svk.transrest.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import svk.transrest.payload.response.ResponseError;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class ValidationErrorHandler {
    public static ResponseEntity<Object> handleValidationErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST, errorMessage, LocalDateTime.now().toString());
            return ResponseEntity.badRequest().body(error);
        }
        return null;
    }
}
