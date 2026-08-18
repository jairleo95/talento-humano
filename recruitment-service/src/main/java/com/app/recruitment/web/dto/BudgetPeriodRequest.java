package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record BudgetPeriodRequest(
        @NotBlank String name,
        @NotNull Instant startDate,
        @NotNull Instant endDate
) {
}