package com.example.webflux_ms_bootcamps.infrastructure.output.mysql.mapper;

import com.example.webflux_ms_bootcamps.domain.model.BootcampModel;
import com.example.webflux_ms_bootcamps.infrastructure.output.mysql.entity.BootcampEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBootcampEntityMapper {
    BootcampEntity toEntity(BootcampModel model);
    BootcampModel toModel(BootcampEntity entity);
}
