package kz.benomads.testproject4sp.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final HttpStatus httpNotFoundErrorStatus = HttpStatus.NOT_FOUND;
    private final HttpStatus httpFoundErrorStatus = HttpStatus.FOUND;
    private final HttpStatus httpBadRequestErrorStatus = HttpStatus.BAD_REQUEST;
    private final HttpStatus httpInternalServerErrorStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(value = {ProductNotFoundException.class})
    public ResponseEntity<Object> handleProductNotFoundException(Exception ex,
                                                                 HttpServletRequest request) {
        ApiException apiException = getApiException(ex, request, httpNotFoundErrorStatus);
        return new ResponseEntity<>(apiException, httpNotFoundErrorStatus);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(Exception ex,
                                                              HttpServletRequest request) {
        ApiException apiException = getApiException(ex, request, httpNotFoundErrorStatus);
        return new ResponseEntity<>(apiException, httpNotFoundErrorStatus);
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserAlreadyExistsException(Exception ex,
                                                                   HttpServletRequest request) {
        ApiException apiException = getApiException(ex, request, httpFoundErrorStatus);
        return new ResponseEntity<>(apiException, httpFoundErrorStatus);
    }

    @ExceptionHandler(value = {OrderNotFoundException.class})
    public ResponseEntity<Object> handleOrderNotFoundException(Exception ex,
                                                               HttpServletRequest request) {
        ApiException apiException = getApiException(ex, request, httpNotFoundErrorStatus);
        return new ResponseEntity<>(apiException, httpNotFoundErrorStatus);
    }

    @ExceptionHandler(value = {NullValueException.class})
    public ResponseEntity<Object> handleNullValueException(Exception ex,
                                                           HttpServletRequest request) {
        ApiException apiException = getApiException(ex, request, httpBadRequestErrorStatus);
        return new ResponseEntity<>(apiException, httpBadRequestErrorStatus);
    }

    @ExceptionHandler(value = {NewsNotFoundException.class})
    public ResponseEntity<Object> handleNewsNotFoundException(Exception ex,
                                                              HttpServletRequest request) {
        ApiException apiException = getApiException(ex, request, httpNotFoundErrorStatus);
        return new ResponseEntity<>(apiException, httpNotFoundErrorStatus);
    }



    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                 HttpServletRequest request) {
        ApiException apiException = getApiException(ex, request, httpBadRequestErrorStatus);
        return new ResponseEntity<>(apiException, httpBadRequestErrorStatus);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, HttpServletRequest request) {
        ApiException apiException = getApiException(ex, request, httpInternalServerErrorStatus);
        return new ResponseEntity<>(apiException, httpInternalServerErrorStatus);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        ApiException apiException = getApiException(ex, request, HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

    //TODO update ApiException, modify the response body
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Object> customValidationErrorHandling(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", "Validation Error");
        body.put("message", ex.getBindingResult().getFieldError().getDefaultMessage());
        body.put("path", request.getDescription(false));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
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

