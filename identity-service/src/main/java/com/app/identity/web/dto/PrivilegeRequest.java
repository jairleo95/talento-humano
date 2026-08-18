package com.app.identity.web.dto;

import jakarta.validation.constraints.NotBlank;

public record PrivilegeRequest(
        @NotBlank(message = "El código es obligatorio") String code,
        @NotBlank(message = "La descripción es obligatoria") String description,
        String linkUrl,
        String icon,
        @NotBlank(message = "El módulo es obligatorio") String moduleName,
        Integer sortOrder
) {
}