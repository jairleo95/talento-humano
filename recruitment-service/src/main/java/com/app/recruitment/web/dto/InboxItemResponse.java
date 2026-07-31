package com.app.recruitment.web.dto;

import java.time.Instant;
import java.util.UUID;

public record InboxItemResponse(
        UUID id,
        UUID requisitionId,
        UUID processStepId,
        String assignee,
        String status,
        Instant createdAt,
        Instant updatedAt
) {
}
