package com.example.webflux_ms_bootcamps.infrastructure.output.mysql.adapter;

import com.example.webflux_ms_bootcamps.domain.model.BootcampModel;
import com.example.webflux_ms_bootcamps.domain.spi.IBootcampPersistencePort;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.entity.BootcampCapabilityEntity;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.entity.BootcampEntity;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.mapper.IBootcampEntityMapper;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.repository.IBootcampCapabilityRepository;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.repository.IBootcampRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BootcampAdapter implements IBootcampPersistencePort {

    private final IBootcampRepository bootcampRepository;
    private final IBootcampCapabilityRepository bootcampCapabilityRepository;
    private final IBootcampEntityMapper bootcampEntityMapper;

    @Override
    public Mono<Void> saveBootcamp(BootcampModel bootcampModel) {
        BootcampEntity entity = bootcampEntityMapper.toEntity(bootcampModel);

        return bootcampRepository.save(entity)
                .flatMapMany(savedBootcamp -> {
                    List<BootcampCapabilityEntity> relations = bootcampModel.getCapabilities().stream()
                            .map(cap -> new BootcampCapabilityEntity(savedBootcamp.getId(), cap.getId()))
                            .collect(Collectors.toList());

                    return bootcampCapabilityRepository.saveAll(relations);
                })
                .then();
    }

    @Override
    public Mono<Boolean> existBootcampByName(String bootcampName) {
        return bootcampRepository.existsBootcampEntitiesByName(bootcampName);
    }
}
