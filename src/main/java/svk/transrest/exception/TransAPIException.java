package svk.transrest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class TransAPIException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;
}
