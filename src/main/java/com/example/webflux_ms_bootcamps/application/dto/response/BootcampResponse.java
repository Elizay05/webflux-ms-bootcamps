package com.example.webflux_ms_bootcamps.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BootcampResponse {
    private Long id;
    private String name;
    private String description;
    private List<CapabilityResponse> capabilities;
}
