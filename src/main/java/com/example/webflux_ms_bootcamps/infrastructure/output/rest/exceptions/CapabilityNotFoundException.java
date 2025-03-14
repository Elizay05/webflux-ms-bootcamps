package com.example.webflux_ms_bootcamps.infrastructure.output.rest.exceptions;

public class CapabilityNotFoundException extends RuntimeException {
    public CapabilityNotFoundException(String message) {
        super(message);
    }
}
