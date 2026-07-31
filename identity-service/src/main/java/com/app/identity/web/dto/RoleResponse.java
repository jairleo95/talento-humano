package com.app.identity.web.dto;

import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name,
        String description
) {
}
