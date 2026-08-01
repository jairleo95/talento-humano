package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DgpCommentRequest(
        @NotNull UUID requisitionId,
        @NotBlank String username,
        @NotBlank String content
) {
}
