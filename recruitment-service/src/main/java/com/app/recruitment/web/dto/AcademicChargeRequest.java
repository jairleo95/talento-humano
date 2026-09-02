package com.app.recruitment.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AcademicChargeRequest(
        @NotNull UUID workerId,
        @NotBlank String semester,
        String faculty,
        String school,
        String educationalSituation,
        String profession,
        String condition,
        String payType,
        @Min(0) Double totalHours,
        LocalDate startDate,
        LocalDate endDate,
        String createdBy,
        @NotNull List<AcademicCourseRequest> courses,
        @NotNull List<AcademicPaymentRequest> payments
) {
}