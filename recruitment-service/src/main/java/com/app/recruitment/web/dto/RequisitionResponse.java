package com.app.recruitment.web.dto;

import java.time.Instant;
import java.util.UUID;

public record RequisitionResponse(
        UUID id,
        String title,
        String description,
        String requestNumber,
        String payrollTypeId,
        String positionId,
        UUID costCenterId,
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
        String bankAccount,
        String status,
        Instant createdAt,
        Instant updatedAt,
        String createdBy,
        String workerId,
        String motive,
        Boolean isMfl,
        Boolean isBudgeted,
        String ruc,
        Double positionBonus,
        Double bevBonus,
        Double familyAllowance,
        String subsidy,
        Double honorariumAmount
) {
}
