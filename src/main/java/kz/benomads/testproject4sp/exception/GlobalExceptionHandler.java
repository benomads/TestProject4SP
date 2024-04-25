package kz.benomads.testproject4sp.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final HttpStatus httpNotFoundErrorStatus = HttpStatus.NOT_FOUND;
    private final HttpStatus httpFoundErrorStatus = HttpStatus.FOUND;

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(Exception ex, HttpServletRequest request) {
        ApiException apiException = getApiException(ex, request, httpNotFoundErrorStatus);
        return new ResponseEntity<>(apiException, httpNotFoundErrorStatus);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(Exception ex, HttpServletRequest request) {
        ApiException apiException = getApiException(ex, request, httpNotFoundErrorStatus);
        return new ResponseEntity<>(apiException, httpNotFoundErrorStatus);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(Exception ex, HttpServletRequest request) {
        ApiException apiException = getApiException(ex, request, httpFoundErrorStatus);
        return new ResponseEntity<>(apiException, httpFoundErrorStatus);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(Exception ex, HttpServletRequest request) {
        ApiException apiException = getApiException(ex, request, httpNotFoundErrorStatus);
        return new ResponseEntity<>(apiException, httpNotFoundErrorStatus);
    }





    private static ApiException getApiException(Exception ex,
                                                HttpServletRequest request,
                                                HttpStatus httpStatus) {
        return
            new ApiException(
                ZonedDateTime.now(ZoneId.of("UTC+5")),
                httpStatus.value(),
                httpStatus,
                ex.getMessage(),
                request.getRequestURI()
            );
    }

}

