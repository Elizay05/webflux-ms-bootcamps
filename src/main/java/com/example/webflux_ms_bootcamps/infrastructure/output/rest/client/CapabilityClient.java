package com.example.webflux_ms_bootcamps.infrastructure.output.rest.client;

import com.example.webflux_ms_bootcamps.domain.model.CapabilityModel;
import com.example.webflux_ms_bootcamps.domain.spi.ICapabilityPersistencePort;
import com.example.webflux_ms_bootcamps.infrastructure.output.rest.exceptions.CapabilityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.webflux_ms_bootcamps.infrastructure.output.rest.utils.constants.ConstantsRest.BODY_CAPABILITY_IDS;
import static com.example.webflux_ms_bootcamps.infrastructure.output.rest.utils.constants.ConstantsRest.PATH_CAPABILITIES_BY_IDS;

@Component
@RequiredArgsConstructor
public class CapabilityClient implements ICapabilityPersistencePort {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<List<CapabilityModel>> getCapabilitiesByIds(List<Long> capabilityIds) {
        return webClientBuilder.build()
                .post()
                .uri(PATH_CAPABILITIES_BY_IDS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Collections.singletonMap(BODY_CAPABILITY_IDS, capabilityIds))
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse ->
                        clientResponse.bodyToMono(Map.class)
                                .flatMap(errorBody -> Mono.error(
                                        new CapabilityNotFoundException(errorBody.get("message").toString())
                                ))
                )
                .bodyToMono(new ParameterizedTypeReference<List<CapabilityModel>>() {});
    }
}
