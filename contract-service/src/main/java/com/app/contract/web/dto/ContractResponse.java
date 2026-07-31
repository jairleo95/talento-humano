package com.app.contract.web.dto;

import java.time.Instant;
import java.util.UUID;

public record ContractResponse(
        UUID id,
        UUID requisitionId,
        UUID templateId,
        String contractNumber,
        String positionId,
        String workerId,
        Instant startDate,
        Instant endDate,
        Instant terminationDate,
        String conditionType,
        Double salaryAmount,
        Double reintegrationAmount,
        Double familyAllowance,
        Double weeklyHours,
        Double dailyHours,
        String laborRegime,
        String pensionRegime,
        String contractType,
        String observation,
        String status,
        Instant signedAt,
        Instant createdAt,
        Instant updatedAt,
        String createdBy,
        String updatedBy
) {
}
