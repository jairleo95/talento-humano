package com.app.recruitment.web;

import com.app.recruitment.domain.Worker;
import com.app.recruitment.web.dto.WorkerRequest;
import com.app.recruitment.web.dto.WorkerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring", imports = { Instant.class })
public interface WorkerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    Worker toEntity(WorkerRequest request);

    WorkerResponse toResponse(Worker entity);
}
