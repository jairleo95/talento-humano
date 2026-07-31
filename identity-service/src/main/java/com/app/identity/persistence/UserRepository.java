package com.app.identity.persistence;

import com.app.identity.domain.UserAccount;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends R2dbcRepository<UserAccount, UUID> {

    Mono<Boolean> existsByUsername(String username);
}
