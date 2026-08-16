package com.app.identity.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthWebFilter implements WebFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ADMIN_ROLE = "ADMIN";

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/api/v1/auth/login"
    );
    private static final Set<String> ADMIN_PATHS = Set.of(
            "/api/v1/users",
            "/api/v1/roles",
            "/api/v1/privileges"
    );

    private final JwtService jwtService;

    public JwtAuthWebFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (isPublic(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return writeError(exchange, HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(BEARER_PREFIX.length());
        JwtService.TokenClaims claims;
        try {
            claims = jwtService.parse(token);
        } catch (Exception ex) {
            return writeError(exchange, HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

        if (requiresAdminRole(exchange, path) && !claims.roles().contains(ADMIN_ROLE)) {
            return writeError(exchange, HttpStatus.FORBIDDEN, "Insufficient privileges");
        }

        return chain.filter(exchange);
    }

    private boolean requiresAdminRole(ServerWebExchange exchange, String path) {
        HttpMethod method = exchange.getRequest().getMethod();
        if (method == null) {
            return false;
        }
        boolean isWrite = method == HttpMethod.POST || method == HttpMethod.PUT
                || method == HttpMethod.PATCH || method == HttpMethod.DELETE;
        if (!isWrite) {
            return false;
        }
        return ADMIN_PATHS.stream().anyMatch(path::startsWith);
    }

    private boolean isPublic(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::equals)
                || path.startsWith("/actuator/");
    }

    private Mono<Void> writeError(ServerWebExchange exchange, HttpStatus status, String message) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] body = ("{\"error\":\"" + message + "\"}").getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
