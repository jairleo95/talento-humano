package com.app.identity.service;

import com.app.identity.config.JwtService;
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
    private static final String ACCOUNT_DISABLED = "Account is disabled";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;
    private final PrivilegeRepository privilegeRepository;
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
