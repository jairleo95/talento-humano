package com.app.contract.web;

import com.app.contract.domain.ContractAttachment;
import com.app.contract.web.dto.AttachmentRequest;
import com.app.contract.web.dto.AttachmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring", imports = { Instant.class })
public interface AttachmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    ContractAttachment toEntity(AttachmentRequest request);

    AttachmentResponse toResponse(ContractAttachment entity);
}
