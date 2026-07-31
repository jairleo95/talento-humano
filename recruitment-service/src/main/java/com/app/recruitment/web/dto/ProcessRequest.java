package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProcessRequest(
        @NotBlank @Size(max = 140) String name,
        @NotBlank @Size(max = 50) String code,
        String description
) {
}
