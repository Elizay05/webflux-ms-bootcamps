package com.example.webflux_ms_bootcamps.domain.spi;

import com.example.webflux_ms_bootcamps.domain.model.BootcampModel;
import com.example.webflux_ms_bootcamps.domain.model.BootcampPageModel;
import reactor.core.publisher.Mono;

public interface IBootcampPersistencePort {
    Mono<Void> saveBootcamp(BootcampModel bootcampModel);
    Mono<Boolean> existBootcampByName(String bootcampName);
    Mono<BootcampPageModel> getAllBootcamps(int page, int size, boolean asc, String sortBy);
}
