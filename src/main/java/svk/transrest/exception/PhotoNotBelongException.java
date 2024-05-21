package svk.transrest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class PhotoNotBelongException extends RuntimeException {
    private final long photoId;
    private final long elementId;

    public PhotoNotBelongException(long photoId, long carId) {
        super(String.format("Photo with id '%d' doesn't belong to car with id '%d'", photoId, carId));
        this.photoId = photoId;
        this.elementId = carId;
    }

}
