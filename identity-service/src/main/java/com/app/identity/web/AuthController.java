package com.app.identity.web;

import com.app.identity.config.JwtService;
import com.app.identity.service.AuthService;
import com.app.identity.web.dto.LoginRequest;
import com.app.identity.web.dto.LoginResponse;
import com.app.identity.web.dto.MeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private static final String BEARER_PREFIX = "Bearer ";

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/auth/login")
    public Mono<LoginResponse> login(@RequestBody @Validated LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/users/me")
    public Mono<MeResponse> me(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return parseToken(authHeader)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or missing token")))
                .flatMap(authService::me);
    }

    private Mono<java.util.UUID> parseToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return Mono.empty();
        }
        try {
            String token = authHeader.substring(BEARER_PREFIX.length());
            return Mono.just(jwtService.parse(token).userId());
        } catch (Exception ex) {
            return Mono.empty();
        }
    }
}
