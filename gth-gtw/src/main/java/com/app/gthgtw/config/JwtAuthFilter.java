package com.app.gthgtw.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String CLAIM_ROLES = "roles";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String TOKEN_ISSUER = "gth-identity";
    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/identity/api/v1/auth/login",
            "/gth/valida"
    );
    private static final String ME_PATH = "/identity/api/v1/users/me";
    private static final String LEGACY_ADMIN_PATH = "/gth";
    private static final String IDENTITY_ADMIN_PATH = "/identity/api/v1";
    private static final Set<String> DATA_ADMIN_PATHS = Set.of("/recruitment", "/contract");
    private static final String PROMETHEUS_PATH = "/actuator/prometheus";
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private final SecretKey key;

    public JwtAuthFilter(@Value("${app.jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(BEARER_PREFIX.length());
        Set<String> roles;
        try {
            Claims claims = Jwts.parser().requireIssuer(TOKEN_ISSUER).verifyWith(key).build().parseSignedClaims(token).getPayload();
            roles = parseRoles(claims);
        } catch (Exception ex) {
            return unauthorized(exchange, "Invalid or expired token");
        }

        if (requiresAdminRole(request, path) && !roles.contains(ADMIN_ROLE)) {
            return forbidden(exchange);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private boolean requiresAdminRole(ServerHttpRequest request, String path) {
        HttpMethod method = request.getMethod();
        if (method == null) {
            return false;
        }
        if (path.startsWith(ME_PATH)) {
            return false;
        }
        if (PATH_MATCHER.match(PROMETHEUS_PATH, path)) {
            return true;
        }
        boolean isIdentityAdminPath = PATH_MATCHER.match(IDENTITY_ADMIN_PATH + "/**", path)
                || PATH_MATCHER.match(IDENTITY_ADMIN_PATH, path);
        if (isIdentityAdminPath && (method == HttpMethod.GET || isWriteMethod(method))) {
            return true;
        }
        boolean isLegacyAdminPath = PATH_MATCHER.match(LEGACY_ADMIN_PATH + "/**", path);
        if (isLegacyAdminPath) {
            return true;
        }
        boolean isDataPath = DATA_ADMIN_PATHS.stream()
                .anyMatch(p -> PATH_MATCHER.match(p + "/**", path) || PATH_MATCHER.match(p, path));
        return isDataPath;
    }

    private boolean isWriteMethod(HttpMethod method) {
        return method == HttpMethod.POST || method == HttpMethod.PUT
                || method == HttpMethod.PATCH || method == HttpMethod.DELETE;
    }

    private Set<String> parseRoles(Claims claims) {
        Object rawRoles = claims.get(CLAIM_ROLES);
        if (rawRoles instanceof List<?> rolesList) {
            return rolesList.stream().map(String::valueOf).collect(java.util.stream.Collectors.toUnmodifiableSet());
        }
        return Set.of();
    }

    private boolean isPublicPath(String path) {
        return path.equals("/actuator/health") || path.equals("/actuator/info")
                || PUBLIC_PATHS.stream().anyMatch(p -> PATH_MATCHER.match(p, path));
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return writeError(exchange, message);
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return writeError(exchange, "Insufficient privileges");
    }

    private Mono<Void> writeError(ServerWebExchange exchange, String message) {
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] body = ("{\"error\":\"" + message + "\"}").getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
