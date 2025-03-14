package com.example.webflux_ms_bootcamps.infrastructure.output.mysql.adapter;

import com.example.webflux_ms_bootcamps.domain.model.BootcampModel;
import com.example.webflux_ms_bootcamps.domain.model.CapabilityModel;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.entity.BootcampCapabilityEntity;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.entity.BootcampEntity;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.mapper.IBootcampEntityMapper;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.repository.IBootcampCapabilityRepository;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.repository.IBootcampRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BootcampAdapterTest {
    @Mock
    private IBootcampRepository bootcampRepository;

    @Mock
    private IBootcampCapabilityRepository bootcampCapabilityRepository;

    @Mock
    private IBootcampEntityMapper bootcampEntityMapper;

    @InjectMocks
    private BootcampAdapter bootcampAdapter;

    @Test
    public void test_save_bootcamp_with_capabilities_successfully() {
        BootcampModel bootcampModel = new BootcampModel(null, "Java Bootcamp", "Description", List.of(
                new CapabilityModel(1L, "Java", "Java Programming", List.of()),
                new CapabilityModel(2L, "Spring", "Spring Framework", List.of())
        ));

        BootcampEntity bootcampEntity = new BootcampEntity(null, "Java Bootcamp", "Description");
        BootcampEntity savedBootcampEntity = new BootcampEntity(1L, "Java Bootcamp", "Description");

        BootcampCapabilityEntity relation1 = new BootcampCapabilityEntity(1L, 1L);
        BootcampCapabilityEntity relation2 = new BootcampCapabilityEntity(1L, 2L);

        Mockito.when(bootcampEntityMapper.toEntity(bootcampModel)).thenReturn(bootcampEntity);
        Mockito.when(bootcampRepository.save(bootcampEntity)).thenReturn(Mono.just(savedBootcampEntity));
        Mockito.when(bootcampCapabilityRepository.saveAll(Mockito.anyList()))
                .thenReturn(Flux.just(relation1, relation2));

        // Act
        Mono<Void> result = bootcampAdapter.saveBootcamp(bootcampModel);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        Mockito.verify(bootcampEntityMapper).toEntity(bootcampModel);
        Mockito.verify(bootcampRepository).save(bootcampEntity);
        Mockito.verify(bootcampCapabilityRepository).saveAll(Mockito.anyList());
    }

    @Test
    public void test_returns_true_when_bootcamp_exists() {
        // Arrange
        String bootcampName = "Java Bootcamp";
        Mockito.when(bootcampRepository.existsBootcampEntitiesByName(bootcampName))
                .thenReturn(Mono.just(true));

        // Act
        Mono<Boolean> result = bootcampAdapter.existBootcampByName(bootcampName);

        // Assert
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }
}
