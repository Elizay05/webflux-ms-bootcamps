package com.example.webflux_ms_bootcamps.infrastructure.input.handler;

import com.example.webflux_ms_bootcamps.application.dto.request.BootcampRequest;
import com.example.webflux_ms_bootcamps.application.handler.IBootcampRestHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BootcampHandlerTest {

    @Mock
    private IBootcampRestHandler bootcampRestHandler;

    @Mock
    private Validator validator;

    @InjectMocks
    private BootcampHandler bootcampHandler;

    @Test
    public void test_valid_bootcamp_request_returns_created_status() {
        // Arrange
        ServerRequest request = MockServerRequest.builder()
                .body(Mono.just(new BootcampRequest("Test Bootcamp", "Test Description", List.of(1L, 2L))));

        when(validator.validate(any(BootcampRequest.class))).thenReturn(Collections.emptySet());
        when(bootcampRestHandler.createBootcamp(any(BootcampRequest.class))).thenReturn(Mono.empty());

        BootcampHandler bootcampHandler = new BootcampHandler(bootcampRestHandler, validator);

        // Act
        Mono<ServerResponse> responseMono = bootcampHandler.createBootcamp(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.statusCode() == HttpStatus.CREATED)
                .verifyComplete();

        verify(bootcampRestHandler).createBootcamp(any(BootcampRequest.class));
    }

    @Test
    public void test_invalid_bootcamp_request_returns_bad_request() {
        // Arrange
        BootcampRequest invalidRequest = new BootcampRequest("", "", Collections.emptyList());
        ServerRequest request = MockServerRequest.builder()
                .body(Mono.just(invalidRequest));

        Set<ConstraintViolation<BootcampRequest>> violations = new HashSet<>();
        ConstraintViolation<BootcampRequest> violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);

        when(path.toString()).thenReturn("name");
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("is required");
        violations.add(violation);

        Validator validator = mock(Validator.class);
        IBootcampRestHandler bootcampRestHandler = mock(IBootcampRestHandler.class);

        when(validator.validate(any(BootcampRequest.class))).thenReturn(violations);

        BootcampHandler bootcampHandler = new BootcampHandler(bootcampRestHandler, validator);

        // Act
        Mono<ServerResponse> responseMono = bootcampHandler.createBootcamp(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> {
                    return response.statusCode() == HttpStatus.BAD_REQUEST &&
                            response.headers().getContentType().equals(MediaType.APPLICATION_JSON);
                })
                .verifyComplete();

        verify(bootcampRestHandler, never()).createBootcamp(any(BootcampRequest.class));
    }
}
