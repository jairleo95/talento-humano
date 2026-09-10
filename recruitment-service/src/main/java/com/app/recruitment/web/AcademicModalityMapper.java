package com.app.recruitment.web;

import com.app.recruitment.domain.AcademicModality;
import com.app.recruitment.web.dto.AcademicModalityRequest;
import com.app.recruitment.web.dto.AcademicModalityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring", imports = { Instant.class })
public interface AcademicModalityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "sortOrder", expression = "java(request.sortOrder() != null ? request.sortOrder() : 0)")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    AcademicModality toEntity(AcademicModalityRequest request);

    AcademicModalityResponse toResponse(AcademicModality entity);
}
