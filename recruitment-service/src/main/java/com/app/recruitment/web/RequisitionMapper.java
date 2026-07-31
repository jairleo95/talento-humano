package com.app.recruitment.web;

import com.app.recruitment.domain.Requisition;
import com.app.recruitment.web.dto.RequisitionRequest;
import com.app.recruitment.web.dto.RequisitionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = { Instant.class, UUID.class })
public interface RequisitionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "costCenterId", expression = "java(request.costCenterId() == null ? null : UUID.fromString(request.costCenterId()))")
    @Mapping(target = "status", constant = "OPEN")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    Requisition toEntity(RequisitionRequest request);

    RequisitionResponse toResponse(Requisition entity);
}
