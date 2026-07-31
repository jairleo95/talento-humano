package com.app.contract.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TemplateRequest(
        @NotBlank @Size(max = 140) String name,
        @NotNull Integer version,
        @NotBlank String content,
        String fileName,
        String status,
        String createdBy
) {
}
