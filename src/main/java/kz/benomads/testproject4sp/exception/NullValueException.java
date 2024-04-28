package kz.benomads.testproject4sp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NullValueException extends RuntimeException {
    public NullValueException(String message) {
        super(message);
    }
}
