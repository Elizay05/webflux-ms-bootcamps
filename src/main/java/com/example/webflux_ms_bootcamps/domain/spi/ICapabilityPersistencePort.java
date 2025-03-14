package com.example.webflux_ms_bootcamps.domain.spi;

import com.example.webflux_ms_bootcamps.domain.model.CapabilityModel;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapabilityPersistencePort {
    Mono<List<CapabilityModel>> getCapabilitiesByIds(List<Long> capabilityIds);
}
