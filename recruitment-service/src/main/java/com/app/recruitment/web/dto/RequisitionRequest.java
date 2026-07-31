package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record RequisitionRequest(
        @NotBlank @Size(max = 140) String title,
        @NotBlank String description,
        @NotBlank String createdBy,
        @NotBlank String requestNumber,
        String payrollTypeId,
        String positionId,
        String costCenterId,
        Instant startDate,
        Instant endDate,
        Double salaryAmount,
        Double foodBonus,
        String workDays,
        String serviceLocation,
        String serviceDescription,
        String paymentPeriod,
        String fiscalAddress,
        String allowanceDescription,
        String trainingSchedule,
        String breakSchedule,
        String trainingDays,
        String policeRecordDesc,
        String healthCertificateDesc,
        String bankName,
        String bankAccount
) {
}
