package com.app.identity.web.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        Boolean enabled,
        Instant createdAt,
        Set<UUID> roleIds
) {
}
