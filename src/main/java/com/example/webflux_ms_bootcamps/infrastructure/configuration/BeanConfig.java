package com.example.webflux_ms_bootcamps.infrastructure.configuration;

import com.example.webflux_ms_bootcamps.application.handler.IBootcampRestHandler;
import com.example.webflux_ms_bootcamps.application.handler.impl.BootcampRestHandlerImpl;
import com.example.webflux_ms_bootcamps.application.mapper.IBootcampPageResponseMapper;
import com.example.webflux_ms_bootcamps.application.mapper.IBootcampRequestMapper;
import com.example.webflux_ms_bootcamps.domain.api.IBootcampServicePort;
import com.example.webflux_ms_bootcamps.domain.spi.IBootcampPersistencePort;
import com.example.webflux_ms_bootcamps.domain.spi.ICapabilityPersistencePort;
import com.example.webflux_ms_bootcamps.domain.usecase.BootcampUseCase;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.adapter.BootcampAdapter;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.mapper.IBootcampEntityMapper;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.repository.IBootcampCapabilityRepository;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.repository.IBootcampRepository;
import com.example.webflux_ms_bootcamps.infrastructure.output.rest.client.CapabilityClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
    private final IBootcampRepository bootcampRepository;
    private final IBootcampCapabilityRepository bootcampCapabilityRepository;
    private final IBootcampEntityMapper bootcampEntityMapper;
    private final IBootcampRequestMapper bootcampRequestMapper;
    private final IBootcampPageResponseMapper bootcampPageResponseMapper;

    @Bean
    public IBootcampServicePort bootcampServicePort(IBootcampPersistencePort bootcampPersistencePort,
                                                    ICapabilityPersistencePort capabilityPersistencePort) {
        return new BootcampUseCase(bootcampPersistencePort, capabilityPersistencePort);
    }

    @Bean
    public ICapabilityPersistencePort capabilityPersistencePort(WebClient.Builder webClientBuilder) {
        return new CapabilityClient(webClientBuilder);
    }

    @Bean
    public IBootcampPersistencePort bootcampPersistencePort() {
        return new BootcampAdapter(bootcampRepository, bootcampCapabilityRepository, bootcampEntityMapper);
    }

    @Bean
    public IBootcampRestHandler bootcampRestHandler(IBootcampServicePort bootcampServicePort) {
        return new BootcampRestHandlerImpl(bootcampServicePort, bootcampRequestMapper, bootcampPageResponseMapper);
    }
}
