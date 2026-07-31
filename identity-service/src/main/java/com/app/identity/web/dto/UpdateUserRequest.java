package com.app.identity.web.dto;

import java.util.Set;
import java.util.UUID;

public record UpdateUserRequest(
        Boolean enabled,
        String email,
        Set<UUID> roleIds
) {
}
