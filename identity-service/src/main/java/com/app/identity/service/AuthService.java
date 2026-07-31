package com.app.identity.service;

import com.app.identity.config.JwtService;
import com.app.identity.domain.UserAccount;
import com.app.identity.domain.UserRole;
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
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String INVALID_CREDENTIALS = "Invalid username or password";
    private static final String ACCOUNT_DISABLED = "Account is disabled";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public Mono<LoginResponse> login(LoginRequest request) {
        return userRepository.findByUsername(request.username())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, INVALID_CREDENTIALS)))
                .flatMap(user -> validateCredentials(user, request.password()))
                .flatMap(this::generateLoginResponse);
    }

    public Mono<MeResponse> me(UUID userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")))
                .flatMap(this::buildMeResponse);
    }

    private Mono<UserAccount> validateCredentials(UserAccount user, String rawPassword) {
        if (Boolean.FALSE.equals(user.getEnabled())) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, ACCOUNT_DISABLED));
        }
        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, INVALID_CREDENTIALS));
        }
        return Mono.just(user);
    }

    private Mono<LoginResponse> generateLoginResponse(UserAccount user) {
        String token = jwtService.generate(user.getId(), user.getUsername());
        return buildMeResponse(user)
                .map(me -> new LoginResponse(token, "Bearer", jwtService.getExpirationSeconds(), me));
    }

    private Mono<MeResponse> buildMeResponse(UserAccount user) {
        return userRoleRepository.findByUserId(user.getId())
                .map(UserRole::getRoleId)
                .collectList()
                .flatMapMany(roleRepository::findAllById)
                .collectList()
                .map(roles -> new MeResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getEnabled(),
                        user.getCreatedAt(),
                        roles.stream()
                                .map(r -> r.getName())
                                .collect(Collectors.toUnmodifiableSet())
                ));
    }
}
