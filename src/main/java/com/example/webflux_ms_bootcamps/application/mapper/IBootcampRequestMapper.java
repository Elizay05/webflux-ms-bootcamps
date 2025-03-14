package com.example.webflux_ms_bootcamps.application.mapper;

import com.example.webflux_ms_bootcamps.application.dto.request.BootcampRequest;
import com.example.webflux_ms_bootcamps.domain.model.BootcampModel;
import com.example.webflux_ms_bootcamps.domain.model.CapabilityModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBootcampRequestMapper {

    @Mapping(target = "capabilities", source = "capabilities", qualifiedByName = "mapCapabilityIds")
    BootcampModel toBootcampModel(BootcampRequest request);

    @Named("mapCapabilityIds")
    default List<CapabilityModel> mapCapabilityIds(List<Long> capabilityIds) {
        return capabilityIds.stream()
                .map(id -> new CapabilityModel(id, "", "", List.of()))
                .collect(Collectors.toList());
    }
}
