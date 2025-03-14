package com.example.webflux_ms_bootcamps.infrastructure.output.mysql.adapter;

import com.example.webflux_ms_bootcamps.domain.model.BootcampModel;
import com.example.webflux_ms_bootcamps.domain.model.BootcampPageModel;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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

        when(bootcampEntityMapper.toEntity(bootcampModel)).thenReturn(bootcampEntity);
        when(bootcampRepository.save(bootcampEntity)).thenReturn(Mono.just(savedBootcampEntity));
        when(bootcampCapabilityRepository.saveAll(Mockito.anyList()))
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
        when(bootcampRepository.existsBootcampEntitiesByName(bootcampName))
                .thenReturn(Mono.just(true));

        // Act
        Mono<Boolean> result = bootcampAdapter.existBootcampByName(bootcampName);

        // Assert
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    public void test_get_all_bootcamps_default_sorting_by_name_asc() {
        // Arrange
        BootcampEntity bootcamp1 = new BootcampEntity(1L, "Bootcamp B", "Description B");
        BootcampEntity bootcamp2 = new BootcampEntity(2L, "Bootcamp A", "Description A");

        BootcampModel bootcampModel1 = new BootcampModel(1L, "Bootcamp B", "Description B", new ArrayList<>());
        BootcampModel bootcampModel2 = new BootcampModel(2L, "Bootcamp A", "Description A", new ArrayList<>());

        when(bootcampRepository.findAll()).thenReturn(Flux.just(bootcamp1, bootcamp2));
        when(bootcampCapabilityRepository.findByBootcampId(1L)).thenReturn(Flux.empty());
        when(bootcampCapabilityRepository.findByBootcampId(2L)).thenReturn(Flux.empty());
        when(bootcampEntityMapper.toModel(bootcamp1)).thenReturn(bootcampModel1);
        when(bootcampEntityMapper.toModel(bootcamp2)).thenReturn(bootcampModel2);

        // Act
        Mono<BootcampPageModel> result = bootcampAdapter.getAllBootcamps(0, 10, true, "name");

        // Assert
        StepVerifier.create(result)
                .assertNext(bootcampPageModel -> {
                    assertEquals(2, bootcampPageModel.getBootcamps().size());
                    assertEquals(1, bootcampPageModel.getTotalPages());
                    assertEquals(2, bootcampPageModel.getTotalElements());
                    assertEquals("Bootcamp A", bootcampPageModel.getBootcamps().get(0).getName());
                    assertEquals("Bootcamp B", bootcampPageModel.getBootcamps().get(1).getName());
                })
                .verifyComplete();
    }

    @Test
    public void test_get_all_bootcamps_default_sorting_by_name_desc() {
        // Arrange
        BootcampEntity bootcamp1 = new BootcampEntity(1L, "Bootcamp B", "Description B");
        BootcampEntity bootcamp2 = new BootcampEntity(2L, "Bootcamp A", "Description A");

        BootcampModel bootcampModel1 = new BootcampModel(1L, "Bootcamp B", "Description B", new ArrayList<>());
        BootcampModel bootcampModel2 = new BootcampModel(2L, "Bootcamp A", "Description A", new ArrayList<>());

        when(bootcampRepository.findAll()).thenReturn(Flux.just(bootcamp1, bootcamp2));
        when(bootcampCapabilityRepository.findByBootcampId(1L)).thenReturn(Flux.empty());
        when(bootcampCapabilityRepository.findByBootcampId(2L)).thenReturn(Flux.empty());
        when(bootcampEntityMapper.toModel(bootcamp1)).thenReturn(bootcampModel1);
        when(bootcampEntityMapper.toModel(bootcamp2)).thenReturn(bootcampModel2);

        // Act
        Mono<BootcampPageModel> result = bootcampAdapter.getAllBootcamps(0, 10, false, "name");

        // Assert
        StepVerifier.create(result)
                .assertNext(bootcampPageModel -> {
                    assertEquals(2, bootcampPageModel.getBootcamps().size());
                    assertEquals(1, bootcampPageModel.getTotalPages());
                    assertEquals(2, bootcampPageModel.getTotalElements());
                    assertEquals("Bootcamp B", bootcampPageModel.getBootcamps().get(0).getName());
                    assertEquals("Bootcamp A", bootcampPageModel.getBootcamps().get(1).getName());
                })
                .verifyComplete();
    }

    @Test
    public void test_get_all_bootcamps_sorted_by_capability_count_asc() {
        BootcampEntity bootcamp1 = new BootcampEntity(1L, "Bootcamp A", "Description A");
        BootcampEntity bootcamp2 = new BootcampEntity(2L, "Bootcamp B", "Description B");

        CapabilityModel capability1 = new CapabilityModel(1L, "Capability 1", "Description 1", List.of());
        CapabilityModel capability2 = new CapabilityModel(2L, "Capability 2", "Description 2", List.of());

        BootcampModel bootcampModel1 = new BootcampModel(1L, "Bootcamp A", "Description A", List.of(capability1));
        BootcampModel bootcampModel2 = new BootcampModel(2L, "Bootcamp B", "Description B", List.of(capability1, capability2));

        when(bootcampRepository.findAll()).thenReturn(Flux.just(bootcamp1, bootcamp2));
        when(bootcampCapabilityRepository.findByBootcampId(1L)).thenReturn(Flux.just(new BootcampCapabilityEntity(1L, 1L)));
        when(bootcampCapabilityRepository.findByBootcampId(2L)).thenReturn(Flux.just(new BootcampCapabilityEntity(2L, 1L), new BootcampCapabilityEntity(2L, 2L)));
        when(bootcampEntityMapper.toModel(bootcamp1)).thenReturn(bootcampModel1);
        when(bootcampEntityMapper.toModel(bootcamp2)).thenReturn(bootcampModel2);

        // Act
        Mono<BootcampPageModel> result = bootcampAdapter.getAllBootcamps(0, 10, true, "capabilityCount");

        // Assert
        StepVerifier.create(result)
                .assertNext(bootcampPageModel -> {
                    assertEquals(2, bootcampPageModel.getBootcamps().size());
                    assertEquals(1, bootcampPageModel.getTotalPages());
                    assertEquals(2, bootcampPageModel.getTotalElements());
                    assertEquals("Bootcamp A", bootcampPageModel.getBootcamps().get(0).getName());
                    assertEquals("Bootcamp B", bootcampPageModel.getBootcamps().get(1).getName());
                })
                .verifyComplete();
    }

    @Test
    public void test_get_all_bootcamps_sorted_by_capability_count_desc() {
        BootcampEntity bootcamp1 = new BootcampEntity(1L, "Bootcamp A", "Description A");
        BootcampEntity bootcamp2 = new BootcampEntity(2L, "Bootcamp B", "Description B");

        CapabilityModel capability1 = new CapabilityModel(1L, "Capability 1", "Description 1", List.of());
        CapabilityModel capability2 = new CapabilityModel(2L, "Capability 2", "Description 2", List.of());

        BootcampModel bootcampModel1 = new BootcampModel(1L, "Bootcamp A", "Description A", List.of(capability1));
        BootcampModel bootcampModel2 = new BootcampModel(2L, "Bootcamp B", "Description B", List.of(capability1, capability2));

        when(bootcampRepository.findAll()).thenReturn(Flux.just(bootcamp1, bootcamp2));
        when(bootcampCapabilityRepository.findByBootcampId(1L)).thenReturn(Flux.just(new BootcampCapabilityEntity(1L, 1L)));
        when(bootcampCapabilityRepository.findByBootcampId(2L)).thenReturn(Flux.just(new BootcampCapabilityEntity(2L, 1L), new BootcampCapabilityEntity(2L, 2L)));
        when(bootcampEntityMapper.toModel(bootcamp1)).thenReturn(bootcampModel1);
        when(bootcampEntityMapper.toModel(bootcamp2)).thenReturn(bootcampModel2);

        // Act
        Mono<BootcampPageModel> result = bootcampAdapter.getAllBootcamps(0, 10, false, "capabilityCount");

        // Assert
        StepVerifier.create(result)
                .assertNext(bootcampPageModel -> {
                    assertEquals(2, bootcampPageModel.getBootcamps().size());
                    assertEquals(1, bootcampPageModel.getTotalPages());
                    assertEquals(2, bootcampPageModel.getTotalElements());
                    assertEquals("Bootcamp B", bootcampPageModel.getBootcamps().get(0).getName());
                    assertEquals("Bootcamp A", bootcampPageModel.getBootcamps().get(1).getName());
                })
                .verifyComplete();
    }
}
