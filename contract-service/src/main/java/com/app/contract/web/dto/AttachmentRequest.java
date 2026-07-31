package com.app.contract.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AttachmentRequest(
        @NotNull UUID contractId,
        @NotBlank String filename,
        @NotBlank String contentType,
        @NotBlank String uri,
        Long sizeBytes,
        String checksum
) {
}
