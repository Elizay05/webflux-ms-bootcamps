package com.example.webflux_ms_bootcamps.infrastructure.output.mysql.repository;

import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.entity.BootcampCapabilityEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface IBootcampCapabilityRepository extends R2dbcRepository<BootcampCapabilityEntity, Long> {
}
