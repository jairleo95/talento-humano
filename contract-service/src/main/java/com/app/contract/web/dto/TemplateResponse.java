package com.app.contract.web.dto;

import java.time.Instant;
import java.util.UUID;

public record TemplateResponse(
        UUID id,
        String name,
        Integer version,
        String content,
        String fileName,
        String status,
        Instant createdAt,
        String createdBy
) {
}
