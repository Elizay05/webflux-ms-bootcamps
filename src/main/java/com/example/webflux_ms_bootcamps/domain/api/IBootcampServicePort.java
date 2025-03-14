package com.example.webflux_ms_bootcamps.domain.api;

import com.example.webflux_ms_bootcamps.domain.model.BootcampModel;
import com.example.webflux_ms_bootcamps.domain.model.BootcampPageModel;
import reactor.core.publisher.Mono;

public interface IBootcampServicePort {
    Mono<Void> createBootcamp(BootcampModel bootcampModel);
    Mono<BootcampPageModel> getBootcamps(int page, int size, boolean asc, String sortBy);
}
