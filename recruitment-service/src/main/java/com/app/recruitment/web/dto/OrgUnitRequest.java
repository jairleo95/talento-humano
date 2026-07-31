package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record OrgUnitRequest(
        @NotBlank String name,
        String shortName,
        @NotBlank String unitType,
        UUID parentId,
        Boolean isActive
) {
}
