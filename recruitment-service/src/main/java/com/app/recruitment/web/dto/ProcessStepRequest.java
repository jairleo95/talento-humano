package com.app.recruitment.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProcessStepRequest(
        @NotBlank String name,
        @NotNull @Min(0) Integer orderIndex,
        @NotBlank String code,
        String description,
        Integer slaHours
) {
}
