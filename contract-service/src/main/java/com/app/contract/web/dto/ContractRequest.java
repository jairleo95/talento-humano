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
        String createdBy,
        String directionId,
        String departmentId,
        String areaId,
        String sectionId,
        String branchId,
        Double foodBonus,
        Double bevBonus,
        Double positionBonus,
        Double totalSalary,
        String paymentHourType,
        Boolean isDisability,
        Boolean isBoss,
        String agreementType,
        Instant signingDate,
        Instant vacationStartDate,
        Instant vacationEndDate,
        String currencyType,
        String variableRemuneration,
        String occupationGroupId,
        String subModalityId,
        Boolean isIntern,
        Boolean documentsDelivered,
        Boolean fingerprintRegistered,
        Boolean payrollRegistered,
        String companyRuc,
        String branchCode,
        String specialSituationId,
        String specialSituationDesc
) {
}
