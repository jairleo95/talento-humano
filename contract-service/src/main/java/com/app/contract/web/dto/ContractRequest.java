package com.app.contract.web.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record ContractRequest(
        @NotNull UUID requisitionId,
        @NotNull UUID templateId,
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
        String createdBy
) {
}
