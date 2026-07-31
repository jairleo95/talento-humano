package com.app.identity.persistence;

import com.app.identity.domain.UserRole;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface UserRoleRepository extends R2dbcRepository<UserRole, UUID> {

    Flux<UserRole> findByUserId(UUID userId);
}
