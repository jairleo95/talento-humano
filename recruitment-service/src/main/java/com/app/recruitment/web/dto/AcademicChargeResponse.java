package com.app.recruitment.web.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record AcademicChargeResponse(
        UUID id,
        UUID workerId,
        String workerName,
        String documentNumber,
        String semester,
        String faculty,
        String school,
        String educationalSituation,
        String profession,
        String condition,
        String payType,
        Double totalHours,
        LocalDate startDate,
        LocalDate endDate,
        UUID modalityId,
        String modalityName,
        UUID periodId,
        String periodName,
        String status,
        String createdBy,
        Instant createdAt,
        Instant updatedAt,
        java.util.List<AcademicCourseResponse> courses,
        java.util.List<AcademicPaymentResponse> payments
) {
}