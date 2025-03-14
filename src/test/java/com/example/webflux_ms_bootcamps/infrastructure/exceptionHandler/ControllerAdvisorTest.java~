package com.example.webflux_ms_bootcamps.infrastructure.exceptionHandler;

import com.example.webflux_ms_bootcamps.domain.exceptions.BootcampAlreadyExistsException;
import com.example.webflux_ms_bootcamps.domain.exceptions.DuplicateCapabilitiesException;
import com.example.webflux_ms_bootcamps.domain.exceptions.MaxCapabilitiesBootcampException;
import com.example.webflux_ms_bootcamps.domain.exceptions.MinCapabilitiesBootcampException;
import com.example.webflux_ms_bootcamps.infrastructure.output.rest.exceptions.CapabilityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.webflux_ms_bootcamps.domain.utils.constants.ConstantsDomain.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ControllerAdvisorTest {

    @InjectMocks
    private ControllerAdvisor controllerAdvisor;

    @Test
    public void testBootcampAlreadyExistsException() {
        String errorMessage = BOOTCAMP_ALREADY_EXISTS;
        BootcampAlreadyExistsException exception = new BootcampAlreadyExistsException(errorMessage);

        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleBootcampAlreadyExistsException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ExceptionResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(errorMessage, response.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getStatus());
        assertNotNull(response.getTimestamp());
    }

    @Test
    public void testDuplicateCapabilitiesException() {
        String errorMessage = DUPLICATE_CAPABILITIES_BOOTCAMP_ERROR;
        DuplicateCapabilitiesException exception = new DuplicateCapabilitiesException(errorMessage);

        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleDuplicateCapabilitiesException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ExceptionResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(errorMessage, response.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getStatus());
        assertNotNull(response.getTimestamp());
    }

    @Test
    public void testMaxCapabilitiesBootcampException() {
        String errorMessage = MAX_CAPABILITIES_BOOTCAMP_ERROR;
        MaxCapabilitiesBootcampException exception = new MaxCapabilitiesBootcampException(errorMessage);

        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleMaxCapabilitiesBootcampException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ExceptionResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(errorMessage, response.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getStatus());
        assertNotNull(response.getTimestamp());
    }

    @Test
    public void testMinCapabilitiesBootcampException() {
        String errorMessage = MIN_CAPABILITIES_BOOTCAMP_ERROR;
        MinCapabilitiesBootcampException exception = new MinCapabilitiesBootcampException(errorMessage);

        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleMinCapabilitiesBootcampException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ExceptionResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(errorMessage, response.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getStatus());
        assertNotNull(response.getTimestamp());
    }

    @Test
    public void testCapabilityNotFoundException() {
        String errorMessage = "Capability not found";
        CapabilityNotFoundException exception = new CapabilityNotFoundException(errorMessage);

        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleCapabilityNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ExceptionResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(errorMessage, response.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.toString(), response.getStatus());
        assertNotNull(response.getTimestamp());
    }

}
