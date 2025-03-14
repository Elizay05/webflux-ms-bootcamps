package com.example.webflux_ms_bootcamps.infrastructure.exceptionHandler;

import com.example.webflux_ms_bootcamps.domain.exceptions.BootcampAlreadyExistsException;
import com.example.webflux_ms_bootcamps.domain.exceptions.DuplicateCapabilitiesException;
import com.example.webflux_ms_bootcamps.domain.exceptions.MaxCapabilitiesBootcampException;
import com.example.webflux_ms_bootcamps.domain.exceptions.MinCapabilitiesBootcampException;
import com.example.webflux_ms_bootcamps.infrastructure.output.rest.exceptions.CapabilityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(BootcampAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleBootcampAlreadyExistsException(BootcampAlreadyExistsException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    @ExceptionHandler(DuplicateCapabilitiesException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateCapabilitiesException(DuplicateCapabilitiesException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    @ExceptionHandler(MaxCapabilitiesBootcampException.class)
    public ResponseEntity<ExceptionResponse> handleMaxCapabilitiesBootcampException(MaxCapabilitiesBootcampException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    @ExceptionHandler(MinCapabilitiesBootcampException.class)
    public ResponseEntity<ExceptionResponse> handleMinCapabilitiesBootcampException(MinCapabilitiesBootcampException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    @ExceptionHandler(CapabilityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCapabilityNotFoundException(CapabilityNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
}
