package com.example.webflux_ms_bootcamps.infrastructure.output.mysql.repository;

import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.entity.BootcampEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface IBootcampRepository extends R2dbcRepository<BootcampEntity, Long> {
    Mono<Boolean> existsBootcampEntitiesByName(String name);
}
