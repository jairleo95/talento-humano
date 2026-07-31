package com.app.contract.web.dto;

import java.time.Instant;
import java.util.UUID;

public record AttachmentResponse(
        UUID id,
        UUID contractId,
        String filename,
        String contentType,
        String uri,
        Long sizeBytes,
        String checksum,
        Instant createdAt
) {
}
