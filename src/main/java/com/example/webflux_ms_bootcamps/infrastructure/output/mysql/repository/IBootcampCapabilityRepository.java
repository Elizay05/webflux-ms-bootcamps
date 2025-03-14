package com.example.webflux_ms_bootcamps.infrastructure.output.mysql.repository;

import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.entity.BootcampCapabilityEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface IBootcampCapabilityRepository extends R2dbcRepository<BootcampCapabilityEntity, Long> {
    Flux<BootcampCapabilityEntity> findByBootcampId(Long bootcampId);
}
