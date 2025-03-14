package com.example.webflux_ms_bootcamps.domain;

import com.example.webflux_ms_bootcamps.domain.exceptions.BootcampAlreadyExistsException;
import com.example.webflux_ms_bootcamps.domain.exceptions.DuplicateCapabilitiesException;
import com.example.webflux_ms_bootcamps.domain.exceptions.MaxCapabilitiesBootcampException;
import com.example.webflux_ms_bootcamps.domain.exceptions.MinCapabilitiesBootcampException;
import com.example.webflux_ms_bootcamps.domain.model.BootcampModel;
import com.example.webflux_ms_bootcamps.domain.model.CapabilityModel;
import com.example.webflux_ms_bootcamps.domain.model.TechnologyModel;
import com.example.webflux_ms_bootcamps.domain.spi.IBootcampPersistencePort;
import com.example.webflux_ms_bootcamps.domain.spi.ICapabilityPersistencePort;
import com.example.webflux_ms_bootcamps.domain.usecase.BootcampUseCase;
import com.example.webflux_ms_bootcamps.domain.utils.constants.ConstantsDomain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.webflux_ms_bootcamps.domain.utils.constants.ConstantsDomain.*;
import static java.util.List.of;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BootcampUseCaseTest {

    @Mock
    private IBootcampPersistencePort bootcampPersistencePort;

    @Mock
    private ICapabilityPersistencePort capabilityPersistencePort;

    @InjectMocks
    private BootcampUseCase bootcampUseCase;

    @Test
    public void test_create_bootcamp_success() {
        List<TechnologyModel> technologies = Arrays.asList(
                new TechnologyModel(1L, "Java", "Java Programming"),
                new TechnologyModel(2L, "Spring", "Spring Framework"),
                new TechnologyModel(3L, "React", "React Framework")
        );

        CapabilityModel capability = new CapabilityModel(1L, "Java", "Java Programming", technologies);
        List<CapabilityModel> capabilities = of(capability);
        BootcampModel bootcampModel = new BootcampModel(1L, "Java Bootcamp", "Java Programming Bootcamp", capabilities);

        when(capabilityPersistencePort.getCapabilitiesByIds(of(1L)))
                .thenReturn(Mono.just(capabilities));
        when(bootcampPersistencePort.existBootcampByName("Java Bootcamp"))
                .thenReturn(Mono.just(false));
        when(bootcampPersistencePort.saveBootcamp(bootcampModel))
                .thenReturn(Mono.empty());

        // Act
        Mono<Void> result = bootcampUseCase.createBootcamp(bootcampModel);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        Mockito.verify(bootcampPersistencePort).saveBootcamp(bootcampModel);
    }

    @Test
    public void test_create_bootcamp_fails_due_to_min_capabilities() {
        // Arrange
        List<CapabilityModel> capabilities = Collections.emptyList();
        BootcampModel bootcampModel = new BootcampModel(1L, "Java Bootcamp", "Java Programming Bootcamp", capabilities);

        Mockito.when(capabilityPersistencePort.getCapabilitiesByIds(Mockito.anyList()))
                .thenReturn(Mono.just(Collections.emptyList()));

        // Act & Assert
        StepVerifier.create(bootcampUseCase.createBootcamp(bootcampModel))
                .expectErrorMatches(throwable ->
                        throwable instanceof MinCapabilitiesBootcampException &&
                                throwable.getMessage().equals(ConstantsDomain.MIN_CAPABILITIES_BOOTCAMP_ERROR))
                .verify();

        Mockito.verifyNoInteractions(bootcampPersistencePort);
    }

    @Test
    public void test_create_bootcamp_fails_due_to_max_capabilities() {
        // Arrange
        List<TechnologyModel> technologies = Arrays.asList(
                new TechnologyModel(1L, "Java", "Java Programming"),
                new TechnologyModel(2L, "Spring", "Spring Framework"),
                new TechnologyModel(3L, "React", "React Framework")
        );

        CapabilityModel capability1 = new CapabilityModel(1L, "Java", "Java Programming", technologies);
        CapabilityModel capability2 = new CapabilityModel(2L, "Python", "Python Programming", technologies);
        CapabilityModel capability3 = new CapabilityModel(3L, "Angular", "Angular Programming", technologies);
        CapabilityModel capability4 = new CapabilityModel(4L, "React", "React Programming", technologies);
        CapabilityModel capability5 = new CapabilityModel(5L, "Spring", "Spring Programming", technologies);

        List<CapabilityModel> capabilities = Arrays.asList(capability1, capability2, capability3, capability4, capability5);

        BootcampModel bootcampModel = new BootcampModel(1L, "Java Bootcamp", "Java Programming Bootcamp", capabilities);

        Mockito.when(capabilityPersistencePort.getCapabilitiesByIds(Mockito.anyList()))
                .thenReturn(Mono.just(capabilities));

        // Act & Assert
        StepVerifier.create(bootcampUseCase.createBootcamp(bootcampModel))
                .expectErrorMatches(throwable ->
                        throwable instanceof MaxCapabilitiesBootcampException &&
                                throwable.getMessage().equals(MAX_CAPABILITIES_BOOTCAMP_ERROR))
                .verify();

        Mockito.verifyNoInteractions(bootcampPersistencePort);
    }

    @Test
    public void test_create_bootcamp_fails_due_to_duplicate_capabilities() {
        // Arrange
        List<TechnologyModel> technologies = Arrays.asList(
                new TechnologyModel(1L, "Java", "Java Programming"),
                new TechnologyModel(2L, "Spring", "Spring Framework"),
                new TechnologyModel(3L, "React", "React Framework")
        );

        CapabilityModel capability1 = new CapabilityModel(1L, "Java", "Java Programming", technologies);
        CapabilityModel capability2 = new CapabilityModel(1L, "Java", "Java Programming", technologies);

        List<CapabilityModel> capabilities = Arrays.asList(capability1, capability2);

        BootcampModel bootcampModel = new BootcampModel(1L, "Java Bootcamp", "Java Programming Bootcamp", capabilities);

        Mockito.when(capabilityPersistencePort.getCapabilitiesByIds(Mockito.anyList()))
                .thenReturn(Mono.just(capabilities));

        // Act & Assert
        StepVerifier.create(bootcampUseCase.createBootcamp(bootcampModel))
                .expectErrorMatches(throwable ->
                        throwable instanceof DuplicateCapabilitiesException &&
                                throwable.getMessage().equals(DUPLICATE_CAPABILITIES_BOOTCAMP_ERROR))
                .verify();

        Mockito.verifyNoInteractions(bootcampPersistencePort);
    }

    @Test
    public void test_create_bootcamp_throws_exception_when_name_exists() {
        // Arrange
        List<TechnologyModel> technologies = Arrays.asList(
                new TechnologyModel(1L, "Java", "Java Programming"),
                new TechnologyModel(2L, "Spring", "Spring Framework"),
                new TechnologyModel(3L, "React", "React Framework")
        );

        CapabilityModel capability1 = new CapabilityModel(1L, "Java", "Java Programming", technologies);
        CapabilityModel capability2 = new CapabilityModel(2L, "Angular", "Angular Programming", technologies);

        List<CapabilityModel> capabilities = Arrays.asList(capability1, capability2);

        BootcampModel bootcampModel = new BootcampModel(1L, "Java Bootcamp", "Java Programming Bootcamp", capabilities);

        Mockito.when(bootcampPersistencePort.existBootcampByName("Java Bootcamp"))
                .thenReturn(Mono.just(true));
        Mockito.when(capabilityPersistencePort.getCapabilitiesByIds(Mockito.anyList()))
                .thenReturn(Mono.just(capabilities));

        // Act
        Mono<Void> result = bootcampUseCase.createBootcamp(bootcampModel);

        // Assert
        StepVerifier.create(result)
                .expectError(BootcampAlreadyExistsException.class)
                .verify();

        Mockito.verify(bootcampPersistencePort, Mockito.never()).saveBootcamp(bootcampModel);
    }
}
