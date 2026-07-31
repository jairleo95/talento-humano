package com.app.recruitment.web;

import com.app.recruitment.domain.Requisition;
import com.app.recruitment.web.dto.RequisitionRequest;
import com.app.recruitment.web.dto.RequisitionResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class RequisitionMapper {

    public Requisition toEntity(RequisitionRequest request, UUID id) {
        Instant now = Instant.now();
        return Requisition.builder()
                .id(id)
                .title(request.title())
                .description(request.description())
                .requestNumber(request.requestNumber())
                .payrollTypeId(request.payrollTypeId())
                .positionId(request.positionId())
                .costCenterId(request.costCenterId() == null ? null : UUID.fromString(request.costCenterId()))
                .startDate(request.startDate())
                .endDate(request.endDate())
                .salaryAmount(request.salaryAmount())
                .foodBonus(request.foodBonus())
                .workDays(request.workDays())
                .serviceLocation(request.serviceLocation())
                .serviceDescription(request.serviceDescription())
                .paymentPeriod(request.paymentPeriod())
                .fiscalAddress(request.fiscalAddress())
                .allowanceDescription(request.allowanceDescription())
                .trainingSchedule(request.trainingSchedule())
                .breakSchedule(request.breakSchedule())
                .trainingDays(request.trainingDays())
                .policeRecordDesc(request.policeRecordDesc())
                .healthCertificateDesc(request.healthCertificateDesc())
                .bankName(request.bankName())
                .bankAccount(request.bankAccount())
                .status("OPEN")
                .createdAt(now)
                .updatedAt(now)
                .createdBy(request.createdBy())
                .build();
    }

    public RequisitionResponse toResponse(Requisition entity) {
        return new RequisitionResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getRequestNumber(),
                entity.getPayrollTypeId(),
                entity.getPositionId(),
                entity.getCostCenterId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getSalaryAmount(),
                entity.getFoodBonus(),
                entity.getWorkDays(),
                entity.getServiceLocation(),
                entity.getServiceDescription(),
                entity.getPaymentPeriod(),
                entity.getFiscalAddress(),
                entity.getAllowanceDescription(),
                entity.getTrainingSchedule(),
                entity.getBreakSchedule(),
                entity.getTrainingDays(),
                entity.getPoliceRecordDesc(),
                entity.getHealthCertificateDesc(),
                entity.getBankName(),
                entity.getBankAccount(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy()
        );
    }
}
