package com.app.identity.persistence;

import com.app.identity.domain.Privilege;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface PrivilegeRepository extends R2dbcRepository<Privilege, UUID> {
}
