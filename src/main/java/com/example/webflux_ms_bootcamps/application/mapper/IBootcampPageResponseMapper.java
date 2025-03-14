package com.example.webflux_ms_bootcamps.application.mapper;

import com.example.webflux_ms_bootcamps.application.dto.response.BootcampPageResponse;
import com.example.webflux_ms_bootcamps.domain.model.BootcampPageModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBootcampPageResponseMapper {
    BootcampPageResponse toBootcampPageResponse(BootcampPageModel bootcampPageModel);
}
