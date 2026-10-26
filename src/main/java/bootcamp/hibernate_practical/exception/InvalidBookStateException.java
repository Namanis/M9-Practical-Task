package bootcamp.hibernate_practical.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidBookStateException extends RuntimeException {
    public InvalidBookStateException(String message) {
        super(message);
    }
}
