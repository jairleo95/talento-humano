package com.app.recruitment.web;

import com.app.recruitment.domain.AcademicCharge;
import com.app.recruitment.domain.AcademicCourse;
import com.app.recruitment.domain.AcademicPayment;
import com.app.recruitment.web.dto.AcademicChargeRequest;
import com.app.recruitment.web.dto.AcademicChargeResponse;
import com.app.recruitment.web.dto.AcademicCourseRequest;
import com.app.recruitment.web.dto.AcademicCourseResponse;
import com.app.recruitment.web.dto.AcademicPaymentRequest;
import com.app.recruitment.web.dto.AcademicPaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = { Instant.class, UUID.class })
public interface AcademicChargeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "workerId", expression = "java(request.workerId())")
    @Mapping(target = "status", constant = "BORRADOR")
    @Mapping(target = "createdBy", expression = "java(request.createdBy() != null && !request.createdBy().isBlank() ? request.createdBy() : \"admin\")")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    AcademicCharge toEntity(AcademicChargeRequest request);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chargeId", expression = "java(chargeId)")
    AcademicCourse toEntity(AcademicCourseRequest request, UUID chargeId);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chargeId", expression = "java(chargeId)")
    @Mapping(target = "status", constant = "PENDIENTE")
    AcademicPayment toEntity(AcademicPaymentRequest request, UUID chargeId);

    @Mapping(target = "workerName", ignore = true)
    @Mapping(target = "documentNumber", ignore = true)
    @Mapping(target = "modalityName", ignore = true)
    @Mapping(target = "periodName", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "payments", ignore = true)
    AcademicChargeResponse toResponse(AcademicCharge entity);

    AcademicCourseResponse toResponse(AcademicCourse entity);

    AcademicPaymentResponse toResponse(AcademicPayment entity);
}