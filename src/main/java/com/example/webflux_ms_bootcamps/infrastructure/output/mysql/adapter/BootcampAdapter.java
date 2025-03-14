package com.example.webflux_ms_bootcamps.infrastructure.output.mysql.adapter;

import com.example.webflux_ms_bootcamps.domain.model.BootcampModel;
import com.example.webflux_ms_bootcamps.domain.model.BootcampPageModel;
import com.example.webflux_ms_bootcamps.domain.model.CapabilityModel;
import com.example.webflux_ms_bootcamps.domain.spi.IBootcampPersistencePort;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.entity.BootcampCapabilityEntity;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.entity.BootcampEntity;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.mapper.IBootcampEntityMapper;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.repository.IBootcampCapabilityRepository;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.repository.IBootcampRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.webflux_ms_bootcamps.infrastructure.output.mysql.util.constants.Constants.CAPABILITY_COUNT;

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

    @Override
    public Mono<BootcampPageModel> getAllBootcamps(int page, int size, boolean asc, String sortBy) {
        return bootcampRepository.findAll()
                .collectList()
                .flatMap(bootcamps ->
                        Flux.fromIterable(bootcamps)
                                .flatMap(bootcampEntity ->
                                        bootcampCapabilityRepository.findByBootcampId(bootcampEntity.getId())
                                                .map(relation -> new CapabilityModel(relation.getCapabilityId(), "", "", List.of()))
                                                .collectList()
                                                .map(capabilities -> {
                                                    BootcampModel bootcampModel = bootcampEntityMapper.toModel(bootcampEntity);
                                                    bootcampModel.setCapabilities(capabilities);
                                                    return bootcampModel;
                                                })
                                )
                                .collectList()
                )
                .map(bootcampModels -> {
                    Comparator<BootcampModel> comparator;

                    if (CAPABILITY_COUNT.equals(sortBy)) {
                        comparator = Comparator.comparing(bootcamp -> bootcamp.getCapabilities().size());
                    } else {
                        comparator = Comparator.comparing(BootcampModel::getName, Comparator.nullsLast(Comparator.naturalOrder()));
                    }

                    if (!asc) {
                        comparator = comparator.reversed();
                    }

                    List<BootcampModel> sortedList = bootcampModels.stream()
                            .sorted(comparator)
                            .collect(Collectors.toList());

                    int fromIndex = Math.min(page * size, sortedList.size());
                    int toIndex = Math.min(fromIndex + size, sortedList.size());
                    List<BootcampModel> paginatedList = sortedList.subList(fromIndex, toIndex);

                    return new BootcampPageModel(paginatedList, (int) Math.ceil((double) bootcampModels.size() / size), bootcampModels.size());
                });
    }
}
