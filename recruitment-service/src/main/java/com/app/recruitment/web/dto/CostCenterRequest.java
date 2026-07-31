package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CostCenterRequest(
        @NotBlank String code,
        @NotBlank String name,
        String departmentId,
        Double percentage
) {
}
