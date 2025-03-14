package com.example.webflux_ms_bootcamps.infrastructure.output.rest.client;

import com.example.webflux_ms_bootcamps.domain.model.CapabilityModel;
import com.example.webflux_ms_bootcamps.infrastructure.output.rest.exceptions.CapabilityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.webflux_ms_bootcamps.infrastructure.output.rest.utils.constants.ConstantsRest.BODY_CAPABILITY_IDS;
import static com.example.webflux_ms_bootcamps.infrastructure.output.rest.utils.constants.ConstantsRest.PATH_CAPABILITIES_BY_IDS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CapabilityClientTest {

    @Test
    public void test_successfully_retrieves_capabilities_with_valid_ids() {
        // Arrange
        WebClient.Builder mockWebClientBuilder = mock(WebClient.Builder.class);
        WebClient mockWebClient = mock(WebClient.class);
        WebClient.RequestBodyUriSpec mockRequestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec mockRequestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec mockRequestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec mockResponseSpec = mock(WebClient.ResponseSpec.class);

        List<Long> capabilityIds = Arrays.asList(1L, 2L, 3L);
        List<CapabilityModel> expectedCapabilities = Arrays.asList(
                new CapabilityModel(1L, "Java", "Java Programming", Collections.emptyList()),
                new CapabilityModel(2L, "Spring", "Spring Framework", Collections.emptyList()),
                new CapabilityModel(3L, "React", "React JS", Collections.emptyList())
        );

        when(mockWebClientBuilder.build()).thenReturn(mockWebClient);
        when(mockWebClient.post()).thenReturn(mockRequestBodyUriSpec);
        when(mockRequestBodyUriSpec.uri(PATH_CAPABILITIES_BY_IDS)).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.bodyValue(Collections.singletonMap(BODY_CAPABILITY_IDS, capabilityIds))).thenReturn(mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.onStatus(any(), any())).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToMono(any(ParameterizedTypeReference.class))).thenReturn(Mono.just(expectedCapabilities));

        CapabilityClient capabilityClient = new CapabilityClient(mockWebClientBuilder);

        // Act
        Mono<List<CapabilityModel>> result = capabilityClient.getCapabilitiesByIds(capabilityIds);

        // Assert
        StepVerifier.create(result)
                .expectNext(expectedCapabilities)
                .verifyComplete();
    }

    @Test
    public void test_handles_empty_capability_ids_list() {
        // Arrange
        WebClient.Builder mockWebClientBuilder = mock(WebClient.Builder.class);
        WebClient mockWebClient = mock(WebClient.class);
        WebClient.RequestBodyUriSpec mockRequestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec mockRequestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec mockRequestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec mockResponseSpec = mock(WebClient.ResponseSpec.class);

        List<Long> emptyCapabilityIds = Collections.emptyList();
        List<CapabilityModel> emptyResponse = Collections.emptyList();

        when(mockWebClientBuilder.build()).thenReturn(mockWebClient);
        when(mockWebClient.post()).thenReturn(mockRequestBodyUriSpec);
        when(mockRequestBodyUriSpec.uri(PATH_CAPABILITIES_BY_IDS)).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.bodyValue(Collections.singletonMap(BODY_CAPABILITY_IDS, emptyCapabilityIds))).thenReturn(mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.onStatus(any(), any())).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToMono(any(ParameterizedTypeReference.class))).thenReturn(Mono.just(emptyResponse));

        CapabilityClient capabilityClient = new CapabilityClient(mockWebClientBuilder);

        // Act
        Mono<List<CapabilityModel>> result = capabilityClient.getCapabilitiesByIds(emptyCapabilityIds);

        // Assert
        StepVerifier.create(result)
                .expectNext(emptyResponse)
                .verifyComplete();
    }
}
