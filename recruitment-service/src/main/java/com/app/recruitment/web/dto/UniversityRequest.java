package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;

public record UniversityRequest(
        @NotBlank String name,
        String shortName
) {
}
