package com.app.identity.service;

import com.app.identity.config.JwtService;
import com.app.identity.config.LoginRateLimiter;
import com.app.identity.domain.Privilege;
import com.app.identity.domain.Role;
import com.app.identity.domain.RolePrivilege;
import com.app.identity.domain.UserAccount;
import com.app.identity.domain.UserRole;
import com.app.identity.persistence.PrivilegeRepository;
import com.app.identity.persistence.RolePrivilegeRepository;
import com.app.identity.persistence.RoleRepository;
import com.app.identity.persistence.UserRepository;
import com.app.identity.persistence.UserRoleRepository;
import com.app.identity.web.dto.LoginRequest;
import com.app.identity.web.dto.LoginResponse;
import com.app.identity.web.dto.MeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String INVALID_CREDENTIALS = "Invalid username or password";
    private static final String TOO_MANY_ATTEMPTS = "Too many login attempts. Try again later.";
    private static final String DUMMY_HASH = "$2b$12$904Yk.qLxygkRgFOV47Lpe4O3SWJ6N7Sbuaw8K.1ZxAwhF4FnQ5A6";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;
    private final PrivilegeRepository privilegeRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final LoginRateLimiter rateLimiter;

    public Mono<LoginResponse> login(LoginRequest request) {
        String rateLimitKey = "login:" + request.username();
        if (rateLimiter.isBlocked(rateLimitKey)) {
            return Mono.error(new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, TOO_MANY_ATTEMPTS));
        }
        return userRepository.findByUsername(request.username())
                .defaultIfEmpty(missingUser(request.username()))
                .flatMap(user -> validateCredentials(user, request.password()))
                .flatMap(this::generateLoginResponse)
                .doOnSuccess(response -> rateLimiter.clear(rateLimitKey))
                .doOnError(error -> {
                    if (error instanceof ResponseStatusException statusException
                            && statusException.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()) {
                        rateLimiter.registerFailure(rateLimitKey);
                    }
                });
    }

    private UserAccount missingUser(String username) {
        return new UserAccount(UUID.randomUUID(), 0L, username, "missing@localhost",
                Boolean.TRUE, DUMMY_HASH, java.time.Instant.now());
    }

    public Mono<MeResponse> me(UUID userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")))
                .flatMap(this::buildMeResponse);
    }

    private Mono<UserAccount> validateCredentials(UserAccount user, String rawPassword) {
        if (Boolean.FALSE.equals(user.getEnabled())) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, INVALID_CREDENTIALS));
        }
        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, INVALID_CREDENTIALS));
        }
        return Mono.just(user);
    }

    private Mono<LoginResponse> generateLoginResponse(UserAccount user) {
        return loadRoleNames(user.getId())
                .flatMap(roles -> {
                    String token = jwtService.generate(user.getId(), user.getUsername(), roles);
                    return buildMeResponse(user)
                            .map(me -> new LoginResponse(token, "Bearer", jwtService.getExpirationSeconds(), me));
                });
    }

    private Mono<Set<String>> loadRoleNames(UUID userId) {
        return userRoleRepository.findByUserId(userId)
                .map(UserRole::getRoleId)
                .collectList()
                .flatMap(roleIds -> roleIds.isEmpty()
                        ? Mono.just(Set.of())
                        : roleRepository.findAllById(roleIds)
                                .map(Role::getName)
                                .collect(Collectors.toUnmodifiableSet()));
    }

    private Mono<MeResponse> buildMeResponse(UserAccount user) {
        return userRoleRepository.findByUserId(user.getId())
                .map(UserRole::getRoleId)
                .collectList()
                .flatMap(roleIds -> {
                    if (roleIds.isEmpty()) {
                        return Mono.just(new MeResponse(
                                user.getId(), user.getUsername(), user.getEmail(),
                                user.getEnabled(), user.getCreatedAt(),
                                Set.of(), List.of()
                        ));
                    }
                    return roleRepository.findAllById(roleIds).collectList()
                            .flatMap(roles -> fetchPrivileges(roleIds)
                                    .map(privileges -> new MeResponse(
                                            user.getId(), user.getUsername(), user.getEmail(),
                                            user.getEnabled(), user.getCreatedAt(),
                                            roles.stream().map(Role::getName).collect(Collectors.toUnmodifiableSet()),
                                            privileges
                                    )));
                });
    }

    private Mono<List<MeResponse.PrivilegeInfo>> fetchPrivileges(List<UUID> roleIds) {
        return Flux.fromIterable(roleIds)
                .flatMap(rolePrivilegeRepository::findByRoleIdAndIsActiveTrue)
                .map(RolePrivilege::getPrivilegeId)
                .distinct()
                .collectList()
                .flatMapMany(privilegeRepository::findAllById)
                .sort(Comparator.comparing(Privilege::getSortOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(p -> new MeResponse.PrivilegeInfo(
                        p.getCode(), p.getDescription(),
                        p.getLinkUrl(), p.getIcon(), p.getModuleName(), p.getSortOrder()
                ))
                .collectList();
    }
}
