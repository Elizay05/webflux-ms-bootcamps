package com.example.webflux_ms_bootcamps.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CapabilityResponse {
    private Long id;
    private String name;
    private List<TechnologyResponse> technologies;
}
