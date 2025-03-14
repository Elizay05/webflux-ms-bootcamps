package com.example.webflux_ms_bootcamps.infrastructure.output.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("bootcamp_capability")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootcampCapabilityEntity {
    private Long bootcampId;
    private Long capabilityId;
}
