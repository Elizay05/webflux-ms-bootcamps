package com.example.webflux_ms_bootcamps.domain.exceptions;

public class BootcampAlreadyExistsException extends RuntimeException {
    public BootcampAlreadyExistsException(String message) {
        super(message);
    }
}
