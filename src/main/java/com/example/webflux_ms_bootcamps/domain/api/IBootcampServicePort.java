package com.example.webflux_ms_bootcamps.domain.api;

import com.example.webflux_ms_bootcamps.domain.model.BootcampModel;
import reactor.core.publisher.Mono;

public interface IBootcampServicePort {
    Mono<Void> createBootcamp(BootcampModel bootcampModel);
}
