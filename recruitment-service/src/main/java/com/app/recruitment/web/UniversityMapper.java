package com.app.recruitment.web;

import com.app.recruitment.domain.University;
import com.app.recruitment.web.dto.UniversityRequest;
import com.app.recruitment.web.dto.UniversityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring", imports = { Instant.class })
public interface UniversityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    University toEntity(UniversityRequest request);

    UniversityResponse toResponse(University entity);
}
