package com.app.identity.web.dto;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record MeResponse(
        UUID id,
        String username,
        String email,
        Boolean enabled,
        Instant createdAt,
        Set<String> roles,
        List<PrivilegeInfo> privileges
) {
    public record PrivilegeInfo(
            String code,
            String description,
            String linkUrl,
            String icon,
            String moduleName,
            Integer sortOrder
    ) {}
}
