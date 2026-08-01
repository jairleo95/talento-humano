package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CareerRequest(
        @NotBlank String name,
        UUID universityId
) {
}
