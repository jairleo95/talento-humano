package com.app.identity.config;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginRateLimiter {

    private static final int MAX_ATTEMPTS = 5;
    private static final Duration WINDOW = Duration.ofMinutes(5);
    private static final Duration BLOCK_DURATION = Duration.ofMinutes(15);
    private static final int MAX_ENTRIES = 10_000;

    private final Map<String, AttemptWindow> attempts = new ConcurrentHashMap<>();

    public boolean isBlocked(String key) {
        AttemptWindow window = attempts.get(key);
        if (window == null) {
            return false;
        }
        if (window.blockedUntil != null && Instant.now().isBefore(window.blockedUntil)) {
            return true;
        }
        return false;
    }

    public void registerFailure(String key) {
        Instant now = Instant.now();
        if (attempts.size() >= MAX_ENTRIES) {
            evictStaleEntries(now);
        }
        attempts.compute(key, (k, window) -> {
            AttemptWindow current = (window == null) ? new AttemptWindow() : window;
            if (current.blockedUntil != null && now.isBefore(current.blockedUntil)) {
                return current;
            }
            if (current.failedAt == null || now.isAfter(current.failedAt.plus(WINDOW))) {
                current.failedAt = now;
                current.failCount = 1;
            } else {
                current.failCount++;
            }
            if (current.failCount >= MAX_ATTEMPTS) {
                current.blockedUntil = now.plus(BLOCK_DURATION);
            }
            return current;
        });
    }

    public void clear(String key) {
        attempts.remove(key);
    }

    private void evictStaleEntries(Instant now) {
        attempts.entrySet().removeIf(entry -> {
            AttemptWindow window = entry.getValue();
            return !(window.blockedUntil != null && now.isBefore(window.blockedUntil))
                    && (window.failedAt == null || now.isAfter(window.failedAt.plus(WINDOW)));
        });
    }

    private static final class AttemptWindow {
        private Instant failedAt;
        private int failCount;
        private Instant blockedUntil;
    }
}
