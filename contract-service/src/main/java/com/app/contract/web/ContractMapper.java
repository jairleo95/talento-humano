package com.app.contract.web;

import com.app.contract.domain.Contract;
import com.app.contract.web.dto.ContractRequest;
import com.app.contract.web.dto.ContractResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring", imports = { Instant.class })
public interface ContractMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "status", constant = "DRAFT")
    @Mapping(target = "signedAt", ignore = true)
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedBy", ignore = true)
    Contract toEntity(ContractRequest request);

    ContractResponse toResponse(Contract entity);
}
