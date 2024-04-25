package kz.benomads.testproject4sp.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiException(
    ZonedDateTime timestamp,
    int status,
    HttpStatus error,
    String message,
    String path) {

}