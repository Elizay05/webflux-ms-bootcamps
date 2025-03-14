package com.example.webflux_ms_bootcamps.application.handler;

import com.example.webflux_ms_bootcamps.application.dto.request.BootcampRequest;
import reactor.core.publisher.Mono;

public interface IBootcampRestHandler {
    Mono<Void> createBootcamp(BootcampRequest bootcampRequest);
}
