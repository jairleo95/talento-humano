package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AcademicPeriodRequest(
        @NotBlank(message = "El código es obligatorio")
        @Size(max = 32, message = "Máximo 32 caracteres")
        String code,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 128, message = "Máximo 128 caracteres")
        String name,

        LocalDate startDate,
        LocalDate endDate
) {}
