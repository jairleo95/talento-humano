package com.app.recruitment.web.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record AcademicPeriodResponse(
        UUID id,
        String code,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        Boolean isActive,
        Instant createdAt
) {}
