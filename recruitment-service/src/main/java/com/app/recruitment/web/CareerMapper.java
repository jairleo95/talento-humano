package com.app.recruitment.web;

import com.app.recruitment.domain.Career;
import com.app.recruitment.web.dto.CareerRequest;
import com.app.recruitment.web.dto.CareerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring", imports = { Instant.class })
public interface CareerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    Career toEntity(CareerRequest request);

    CareerResponse toResponse(Career entity);
}
