package com.example.webflux_ms_bootcamps.domain.exceptions;

public class DuplicateCapabilitiesException extends RuntimeException {
    public DuplicateCapabilitiesException(String message) {
        super(message);
    }
}
