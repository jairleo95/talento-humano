package com.app.recruitment.web.dto;

import java.time.Instant;
import java.util.UUID;

public record UniversityResponse(
        UUID id,
        String name,
        String shortName,
        Instant createdAt
) {
}
