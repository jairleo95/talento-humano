package com.app.contract.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("contract")
public class Contract {

    @Id
    private UUID id;

    @Column("requisition_id")
    private UUID requisitionId;

    @Column("template_id")
    private UUID templateId;

    @Column("contract_number")
    private String contractNumber;

    @Column("position_id")
    private String positionId;

    @Column("worker_id")
    private String workerId;

    @Column("start_date")
    private Instant startDate;

    @Column("end_date")
    private Instant endDate;

    @Column("termination_date")
    private Instant terminationDate;

    @Column("condition_type")
    private String conditionType;

    @Column("salary_amount")
    private Double salaryAmount;

    @Column("reintegration_amount")
    private Double reintegrationAmount;

    @Column("family_allowance")
    private Double familyAllowance;

    @Column("weekly_hours")
    private Double weeklyHours;

    @Column("daily_hours")
    private Double dailyHours;

    @Column("labor_regime")
    private String laborRegime;

    @Column("pension_regime")
    private String pensionRegime;

    @Column("contract_type")
    private String contractType;

    @Column("observation")
    private String observation;

    @Column("status")
    private String status;

    @Column("signed_at")
    private Instant signedAt;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;

    @Column("created_by")
    private String createdBy;

    @Column("updated_by")
    private String updatedBy;
}
