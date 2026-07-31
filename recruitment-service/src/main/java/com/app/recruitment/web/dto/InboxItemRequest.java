package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record InboxItemRequest(
        @NotNull UUID requisitionId,
        @NotNull UUID processStepId,
        @NotBlank String assignee
) {
}
