package com.example.webflux_ms_bootcamps.application.handler.impl;

import com.example.webflux_ms_bootcamps.application.dto.request.BootcampRequest;
import com.example.webflux_ms_bootcamps.application.dto.response.BootcampPageResponse;
import com.example.webflux_ms_bootcamps.application.handler.IBootcampRestHandler;
import com.example.webflux_ms_bootcamps.application.mapper.IBootcampPageResponseMapper;
import com.example.webflux_ms_bootcamps.application.mapper.IBootcampRequestMapper;
import com.example.webflux_ms_bootcamps.domain.api.IBootcampServicePort;
import com.example.webflux_ms_bootcamps.domain.model.BootcampModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import static com.example.webflux_ms_bootcamps.application.utils.constants.ConstantsApplication.*;

@RequiredArgsConstructor
public class BootcampRestHandlerImpl implements IBootcampRestHandler {

    private final IBootcampServicePort bootcampServicePort;
    private final IBootcampRequestMapper bootcampRequestMapper;
    private final IBootcampPageResponseMapper bootcampPageResponseMapper;

    @Override
    public Mono<Void> createBootcamp(BootcampRequest bootcampRequest) {
        BootcampModel bootcampModel = bootcampRequestMapper.toBootcampModel(bootcampRequest);
        return bootcampServicePort.createBootcamp(bootcampModel);
    }

    @Override
    public Mono<BootcampPageResponse> getBootcamps(ServerRequest request) {
        int page = Integer.parseInt(request.queryParam(PAGE).orElse(DEFAULT_PAGE));
        int size = Integer.parseInt(request.queryParam(SIZE).orElse(DEFAULT_SIZE));
        boolean asc = Boolean.parseBoolean(request.queryParam(ASC).orElse(DEFAULT_ASC));
        String sortBy = request.queryParam(SORT_BY).orElse(DEFAULT_SORT_BY);

        return bootcampServicePort.getBootcamps(page, size, asc, sortBy)
                .map(bootcampPageResponseMapper::toBootcampPageResponse);
    }
}
