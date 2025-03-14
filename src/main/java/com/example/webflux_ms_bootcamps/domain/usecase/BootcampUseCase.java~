package com.example.webflux_ms_bootcamps.domain.usecase;

import com.example.webflux_ms_bootcamps.domain.api.IBootcampServicePort;
import com.example.webflux_ms_bootcamps.domain.exceptions.BootcampAlreadyExistsException;
import com.example.webflux_ms_bootcamps.domain.exceptions.DuplicateCapabilitiesException;
import com.example.webflux_ms_bootcamps.domain.exceptions.MaxCapabilitiesBootcampException;
import com.example.webflux_ms_bootcamps.domain.exceptions.MinCapabilitiesBootcampException;
import com.example.webflux_ms_bootcamps.domain.model.BootcampModel;
import com.example.webflux_ms_bootcamps.domain.model.CapabilityModel;
import com.example.webflux_ms_bootcamps.domain.spi.IBootcampPersistencePort;
import com.example.webflux_ms_bootcamps.domain.spi.ICapabilityPersistencePort;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.webflux_ms_bootcamps.domain.utils.constants.ConstantsDomain.*;

public class BootcampUseCase implements IBootcampServicePort {

    private final IBootcampPersistencePort bootcampPersistencePort;
    private final ICapabilityPersistencePort capabilityPersistencePort;

    public BootcampUseCase(IBootcampPersistencePort bootcampPersistencePort, ICapabilityPersistencePort capabilityPersistencePort) {
        this.bootcampPersistencePort = bootcampPersistencePort;
        this.capabilityPersistencePort = capabilityPersistencePort;
    }

    @Override
    public Mono<Void> createBootcamp(BootcampModel bootcampModel) {
        return validateBootcamp(bootcampModel)
                .then(verifyCapabilitiesExist(bootcampModel.getCapabilities()))
                .flatMap(capabilities -> {
                    bootcampModel.setCapabilities(capabilities);
                    return existBootcamp(bootcampModel.getName());
                })
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new BootcampAlreadyExistsException(BOOTCAMP_ALREADY_EXISTS));
                    }
                    return bootcampPersistencePort.saveBootcamp(bootcampModel);
                });
    }

    private Mono<List<CapabilityModel>> verifyCapabilitiesExist(List<CapabilityModel> capabilities) {
        List<Long> capabilityIds = capabilities.stream()
                .map(CapabilityModel::getId)
                .collect(Collectors.toList());

        return capabilityPersistencePort.getCapabilitiesByIds(capabilityIds);
    }

    private Mono<Void> validateBootcamp(BootcampModel bootcampModel) {
        List<CapabilityModel> capabilities = bootcampModel.getCapabilities();
        if (capabilities.isEmpty()) {
            return Mono.error(new MinCapabilitiesBootcampException(MIN_CAPABILITIES_BOOTCAMP_ERROR));
        }

        if (capabilities.size() > MAX_CAPABILITIES_BOOTCAMP) {
            return Mono.error(new MaxCapabilitiesBootcampException(MAX_CAPABILITIES_BOOTCAMP_ERROR));
        }

        long uniqueCapabilityCount = capabilities.stream()
                .map(CapabilityModel::getId)
                .distinct()
                .count();
        if (uniqueCapabilityCount != capabilities.size()) {
            return Mono.error(new DuplicateCapabilitiesException(DUPLICATE_CAPABILITIES_BOOTCAMP_ERROR));
        }

        return Mono.just(bootcampModel).then();
    }

    private Mono<Boolean> existBootcamp(String bootcampName) {
        return bootcampPersistencePort.existBootcampByName(bootcampName);
    }
}
