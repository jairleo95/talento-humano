package com.app.recruitment.web;

import com.app.recruitment.domain.AcademicPeriod;
import com.app.recruitment.web.dto.AcademicPeriodRequest;
import com.app.recruitment.web.dto.AcademicPeriodResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring", imports = { Instant.class })
public interface AcademicPeriodMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    AcademicPeriod toEntity(AcademicPeriodRequest request);

    AcademicPeriodResponse toResponse(AcademicPeriod entity);
}
