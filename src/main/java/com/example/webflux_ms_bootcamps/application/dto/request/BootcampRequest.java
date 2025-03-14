package com.example.webflux_ms_bootcamps.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.example.webflux_ms_bootcamps.application.utils.constants.ConstantsApplication.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BootcampRequest {
    @NotBlank(message = IS_REQUIRED)
    private String name;

    @NotBlank(message = IS_REQUIRED)
    private String description;

    @Size(min = 1, max = 4, message = SIZE_CAPABILITIES)
    private List<Long> capabilities;
}
