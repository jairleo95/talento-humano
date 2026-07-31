package com.app.recruitment.web.dto;

import java.time.Instant;
import java.util.UUID;

public record ProcessStepResponse(
        UUID id,
        UUID processId,
        String name,
        String code,
        String description,
        String status,
        Integer orderIndex,
        Integer slaHours,
        Instant createdAt,
        Instant updatedAt
) {
}
