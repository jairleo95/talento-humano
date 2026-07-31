package com.app.recruitment.web.dto;

import java.time.Instant;
import java.util.UUID;

public record CostCenterResponse(
        UUID id,
        String code,
        String name,
        String departmentId,
        Double percentage,
        Instant createdAt
) {
}
