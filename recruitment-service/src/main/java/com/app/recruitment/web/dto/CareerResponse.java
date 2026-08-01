package com.app.recruitment.web.dto;

import java.time.Instant;
import java.util.UUID;

public record CareerResponse(
        UUID id,
        String name,
        UUID universityId,
        Instant createdAt
) {
}
