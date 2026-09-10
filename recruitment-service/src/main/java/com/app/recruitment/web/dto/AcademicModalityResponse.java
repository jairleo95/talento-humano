package com.app.recruitment.web.dto;

import java.time.Instant;
import java.util.UUID;

public record AcademicModalityResponse(
        UUID id,
        String code,
        String name,
        String subModality,
        Integer sortOrder,
        Boolean isActive,
        Instant createdAt
) {}
