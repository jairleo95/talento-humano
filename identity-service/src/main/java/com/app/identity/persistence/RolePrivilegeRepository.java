package com.app.identity.persistence;

import com.app.identity.domain.RolePrivilege;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface RolePrivilegeRepository extends R2dbcRepository<RolePrivilege, UUID> {

    Flux<RolePrivilege> findByRoleIdAndIsActiveTrue(UUID roleId);
}
