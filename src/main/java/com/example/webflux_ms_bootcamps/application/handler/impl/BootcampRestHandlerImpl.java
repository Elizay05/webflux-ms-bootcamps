package com.example.webflux_ms_bootcamps.application.handler.impl;

import com.example.webflux_ms_bootcamps.application.dto.request.BootcampRequest;
import com.example.webflux_ms_bootcamps.application.handler.IBootcampRestHandler;
import com.example.webflux_ms_bootcamps.application.mapper.IBootcampRequestMapper;
import com.example.webflux_ms_bootcamps.domain.api.IBootcampServicePort;
import com.example.webflux_ms_bootcamps.domain.model.BootcampModel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BootcampRestHandlerImpl implements IBootcampRestHandler {

    private final IBootcampServicePort bootcampServicePort;
    private final IBootcampRequestMapper bootcampRequestMapper;

    @Override
    public Mono<Void> createBootcamp(BootcampRequest bootcampRequest) {
        BootcampModel bootcampModel = bootcampRequestMapper.toBootcampModel(bootcampRequest);
        return bootcampServicePort.createBootcamp(bootcampModel);
    }
}
