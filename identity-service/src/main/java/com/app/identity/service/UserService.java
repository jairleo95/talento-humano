package com.app.identity.service;

import com.app.identity.domain.Role;
import com.app.identity.domain.UserAccount;
import com.app.identity.domain.UserRole;
import com.app.identity.persistence.RoleRepository;
import com.app.identity.persistence.UserRepository;
import com.app.identity.persistence.UserRoleRepository;
import com.app.identity.web.UserMapper;
import com.app.identity.web.dto.UserRequest;
import com.app.identity.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper mapper;
    private final ReactiveTransactionManager transactionManager;

    public Flux<UserResponse> findAll() {
        return userRepository.findAll()
                .flatMap(user -> fetchRoleIds(user.getId())
                        .map(roleIds -> mapper.toResponse(user, roleIds)));
    }

    public Mono<UserResponse> findById(UUID id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found: " + id)))
                .flatMap(user -> fetchRoleIds(user.getId())
                        .map(roleIds -> mapper.toResponse(user, roleIds)));
    }

    public Mono<UserResponse> create(UserRequest request) {
        UUID userId = UUID.randomUUID();
        UserAccount entity = mapper.toEntity(request, userId);
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);

        Mono<Void> rolesStep = Mono.justOrEmpty(request.roleIds())
                .flatMapMany(Flux::fromIterable)
                .distinct()
                .flatMap(roleId -> roleRepository.findById(roleId)
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Role not found: " + roleId)))
                        .map(Role::getId))
                .map(roleId -> UserRole.builder()
                        .id(UUID.randomUUID())
                        .userId(userId)
                        .roleId(roleId)
                        .build())
                .flatMap(userRoleRepository::save)
                .then();

        return userRepository.existsByUsername(request.username())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new DuplicateKeyException("Username already exists: " + request.username()));
                    }
                    return userRepository.save(entity)
                            .then(rolesStep)
                            .then(fetchRoleIds(userId)
                                    .map(roleIds -> mapper.toResponse(entity, roleIds)));
                })
                .as(tx::transactional);
    }

    private Mono<Set<UUID>> fetchRoleIds(UUID userId) {
        return userRoleRepository.findByUserId(userId)
                .map(UserRole::getRoleId)
                .collectList()
                .map(Set::copyOf);
    }
}
