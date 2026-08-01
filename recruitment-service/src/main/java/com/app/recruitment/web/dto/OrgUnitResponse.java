package com.app.recruitment.web.dto;

import java.time.Instant;
import java.util.UUID;

public record OrgUnitResponse(
        UUID id,
        String name,
        String shortName,
        String unitType,
        UUID parentId,
        Boolean isActive,
        String occupationGroupCode,
        Instant createdAt,
        Instant updatedAt
) {
}
