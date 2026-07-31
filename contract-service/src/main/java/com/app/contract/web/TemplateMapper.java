package com.app.contract.web;

import com.app.contract.domain.ContractTemplate;
import com.app.contract.web.dto.TemplateRequest;
import com.app.contract.web.dto.TemplateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring", imports = { Instant.class })
public interface TemplateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isNew", constant = "true")
    @Mapping(target = "status", expression = "java(request.status() == null ? \"ACTIVE\" : request.status())")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    ContractTemplate toEntity(TemplateRequest request);

    TemplateResponse toResponse(ContractTemplate entity);
}
