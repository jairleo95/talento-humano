package com.app.contract.web;

import com.app.contract.domain.Contract;
import com.app.contract.web.dto.ContractRequest;
import com.app.contract.web.dto.ContractResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class ContractMapper {

    public Contract toEntity(ContractRequest request, UUID id) {
        Instant now = Instant.now();
        return Contract.builder()
                .id(id)
                .requisitionId(request.requisitionId())
                .templateId(request.templateId())
                .contractNumber(request.contractNumber())
                .positionId(request.positionId())
                .workerId(request.workerId())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .terminationDate(request.terminationDate())
                .conditionType(request.conditionType())
                .salaryAmount(request.salaryAmount())
                .reintegrationAmount(request.reintegrationAmount())
                .familyAllowance(request.familyAllowance())
                .weeklyHours(request.weeklyHours())
                .dailyHours(request.dailyHours())
                .laborRegime(request.laborRegime())
                .pensionRegime(request.pensionRegime())
                .contractType(request.contractType())
                .observation(request.observation())
                .status("DRAFT")
                .createdAt(now)
                .updatedAt(now)
                .createdBy(request.createdBy())
                .build();
    }

    public ContractResponse toResponse(Contract entity) {
        return new ContractResponse(
                entity.getId(),
                entity.getRequisitionId(),
                entity.getTemplateId(),
                entity.getContractNumber(),
                entity.getPositionId(),
                entity.getWorkerId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getTerminationDate(),
                entity.getConditionType(),
                entity.getSalaryAmount(),
                entity.getReintegrationAmount(),
                entity.getFamilyAllowance(),
                entity.getWeeklyHours(),
                entity.getDailyHours(),
                entity.getLaborRegime(),
                entity.getPensionRegime(),
                entity.getContractType(),
                entity.getObservation(),
                entity.getStatus(),
                entity.getSignedAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy()
        );
    }
}
