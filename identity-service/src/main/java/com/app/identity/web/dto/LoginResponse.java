package com.app.identity.web.dto;

public record LoginResponse(
        String token,
        String tokenType,
        long expiresIn,
        MeResponse user
) {
}
