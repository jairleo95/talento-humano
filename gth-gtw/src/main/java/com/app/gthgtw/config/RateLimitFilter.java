package com.app.gthgtw.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitFilter implements GlobalFilter, Ordered {

    private static final String LOGIN_PATH = "/identity/api/v1/auth/login";
    private static final String LEGACY_VALIDA_PATH = "/gth/valida";
    private static final int MAX_ATTEMPTS_PER_WINDOW = 10;
    private static final long WINDOW_MILLIS = 60_000L;
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private final Map<String, Window> buckets = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if (PATH_MATCHER.match(LOGIN_PATH, path) || PATH_MATCHER.match(LEGACY_VALIDA_PATH, path)) {
            String clientIp = resolveClientIp(request);
            if (isBlocked(clientIp)) {
                return writeTooManyRequests(exchange);
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -101;
    }

    private boolean isBlocked(String key) {
        long now = System.currentTimeMillis();
        Window window = buckets.get(key);
        if (window == null || now - window.startedAt >= WINDOW_MILLIS) {
            Window fresh = new Window(now, new AtomicInteger(1));
            Window raced = buckets.putIfAbsent(key, fresh);
            if (raced == null) {
                return false;
            }
            window = raced;
        }
        if (window.attempts.get() > MAX_ATTEMPTS_PER_WINDOW) {
            return true;
        }
        return window.attempts.incrementAndGet() > MAX_ATTEMPTS_PER_WINDOW;
    }

    private String resolveClientIp(ServerHttpRequest request) {
        String forwarded = request.getHeaders().getFirst("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddress() != null ? request.getRemoteAddress().getAddress().getHostAddress() : "unknown";
    }

    private Mono<Void> writeTooManyRequests(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] body = "{\"error\":\"Too many requests. Try again later.\"}".getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    private record Window(long startedAt, AtomicInteger attempts) {
    }
}