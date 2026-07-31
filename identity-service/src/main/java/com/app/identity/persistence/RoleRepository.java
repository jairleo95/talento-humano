package com.app.identity.persistence;

import com.app.identity.domain.Role;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RoleRepository extends R2dbcRepository<Role, UUID> {

    Mono<Role> findByName(String name);
}
