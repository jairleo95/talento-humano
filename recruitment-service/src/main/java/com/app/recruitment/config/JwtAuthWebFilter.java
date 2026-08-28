package com.app.recruitment.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthWebFilter implements WebFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String TOKEN_ISSUER = "gth-identity";
    private static final String CLAIM_ROLES = "roles";
    private static final String ADMIN_ROLE = "ADMIN";

    private final SecretKey key;

    public JwtAuthWebFilter(@Value("${app.jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return writeError(exchange, HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(BEARER_PREFIX.length());
        Set<String> roles;
        try {
            Claims claims = Jwts.parser().requireIssuer(TOKEN_ISSUER).verifyWith(key).build()
                    .parseSignedClaims(token).getPayload();
            roles = parseRoles(claims);
        } catch (Exception ex) {
            return writeError(exchange, HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

        if (requiresAdminRole(path) && !roles.contains(ADMIN_ROLE)) {
            return writeError(exchange, HttpStatus.FORBIDDEN, "Insufficient privileges");
        }

        return chain.filter(exchange);
    }

    private boolean requiresAdminRole(String path) {
        return !isPublicPath(path);
    }

    private Set<String> parseRoles(Claims claims) {
        Object rawRoles = claims.get(CLAIM_ROLES);
        if (rawRoles instanceof List<?> rolesList) {
            return rolesList.stream().map(String::valueOf).collect(java.util.stream.Collectors.toUnmodifiableSet());
        }
        return Set.of();
    }

    private boolean isPublicPath(String path) {
        return path.equals("/actuator/health") || path.equals("/actuator/info");
    }

    private Mono<Void> writeError(ServerWebExchange exchange, HttpStatus status, String message) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] body = ("{\"error\":\"" + message + "\"}").getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}