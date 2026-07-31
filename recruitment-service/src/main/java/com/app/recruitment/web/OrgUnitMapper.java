package com.app.recruitment.web;

import com.app.recruitment.domain.OrganizationalUnit;
import com.app.recruitment.web.dto.OrgUnitRequest;
import com.app.recruitment.web.dto.OrgUnitResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring", imports = { Instant.class })
public interface OrgUnitMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    OrganizationalUnit toEntity(OrgUnitRequest request);

    OrgUnitResponse toResponse(OrganizationalUnit entity);
}
