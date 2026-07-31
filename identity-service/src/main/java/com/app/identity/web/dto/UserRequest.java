package com.app.identity.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record UserRequest(
        @NotBlank String username,
        @NotBlank @Email String email,
        Set<UUID> roleIds
) {
}
