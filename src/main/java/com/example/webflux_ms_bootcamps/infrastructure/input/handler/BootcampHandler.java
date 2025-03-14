package com.example.webflux_ms_bootcamps.infrastructure.input.handler;

import com.example.webflux_ms_bootcamps.application.dto.request.BootcampRequest;
import com.example.webflux_ms_bootcamps.application.handler.IBootcampRestHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.webflux_ms_bootcamps.infrastructure.input.util.constants.ConstantsInput.ERROR;


@Component
@RequiredArgsConstructor
public class BootcampHandler {

    private final IBootcampRestHandler bootcampRestHandler;
    private final Validator validator;

    public Mono<ServerResponse> createBootcamp(ServerRequest request) {
        return request.bodyToMono(BootcampRequest.class)
                .flatMap(bootcampRequest -> {
                    Set<ConstraintViolation<BootcampRequest>> violations = validator.validate(bootcampRequest);
                    if (!violations.isEmpty()) {
                        String errorMessage = violations.stream()
                                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                                .collect(Collectors.joining(", "));

                        return ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("{" + "\"" + ERROR + "\": \"" + errorMessage + "\"}");
                    }

                    return bootcampRestHandler.createBootcamp(bootcampRequest)
                            .then(ServerResponse.status(HttpStatus.CREATED).build());
                });
    }

    public Mono<ServerResponse> getBootcamps(ServerRequest request) {
        return bootcampRestHandler.getBootcamps(request)
                .flatMap(bootcampPage -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(bootcampPage));
    }
}
