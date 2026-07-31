package com.app.recruitment.web.dto;

import java.time.Instant;
import java.util.UUID;

public record ProcessResponse(
        UUID id,
        String name,
        String code,
        String description,
        String status,
        Instant createdAt,
        Instant updatedAt
) {
}
