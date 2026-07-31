package com.app.identity.web;

import com.app.identity.domain.UserAccount;
import com.app.identity.web.dto.UserRequest;
import com.app.identity.web.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = { Instant.class })
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    UserAccount toEntity(UserRequest request);

    default UserResponse toResponse(UserAccount user, Set<UUID> roleIds) {
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
