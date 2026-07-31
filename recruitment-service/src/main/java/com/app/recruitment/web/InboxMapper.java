package com.app.recruitment.web;

import com.app.recruitment.domain.InboxItem;
import com.app.recruitment.web.dto.InboxItemRequest;
import com.app.recruitment.web.dto.InboxItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring", imports = { Instant.class })
public interface InboxMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    InboxItem toEntity(InboxItemRequest request);

    InboxItemResponse toResponse(InboxItem entity);
}
