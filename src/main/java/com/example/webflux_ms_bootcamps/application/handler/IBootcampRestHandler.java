package com.example.webflux_ms_bootcamps.application.handler;

import com.example.webflux_ms_bootcamps.application.dto.request.BootcampRequest;
import com.example.webflux_ms_bootcamps.application.dto.response.BootcampPageResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface IBootcampRestHandler {
    Mono<Void> createBootcamp(BootcampRequest bootcampRequest);
    Mono<BootcampPageResponse> getBootcamps(ServerRequest request);
}
