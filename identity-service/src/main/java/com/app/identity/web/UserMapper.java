package com.app.identity.web;

import com.app.identity.domain.UserAccount;
import com.app.identity.web.dto.UserRequest;
import com.app.identity.web.dto.UserResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Component
public class UserMapper {

    public UserAccount toEntity(UserRequest request, UUID id) {
        return UserAccount.builder()
                .id(id)
                .username(request.username())
                .email(request.email())
                .enabled(Boolean.TRUE)
                .createdAt(Instant.now())
                .build();
    }

    public UserResponse toResponse(UserAccount user, Set<UUID> roleIds) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getEnabled(),
                user.getCreatedAt(),
                roleIds == null ? Collections.emptySet() : roleIds
        );
    }
}
