package com.app.contract.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
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

    @Version
    private Long version;

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

    @Column("direction_id")
    private String directionId;

    @Column("department_id")
    private String departmentId;

    @Column("area_id")
    private String areaId;

    @Column("section_id")
    private String sectionId;

    @Column("branch_id")
    private String branchId;

    @Column("food_bonus")
    private Double foodBonus;

    @Column("bev_bonus")
    private Double bevBonus;

    @Column("position_bonus")
    private Double positionBonus;

    @Column("total_salary")
    private Double totalSalary;

    @Column("payment_hour_type")
    private String paymentHourType;

    @Column("is_disability")
    private Boolean isDisability;

    @Column("is_boss")
    private Boolean isBoss;

    @Column("agreement_type")
    private String agreementType;

    @Column("signing_date")
    private Instant signingDate;

    @Column("vacation_start_date")
    private Instant vacationStartDate;

    @Column("vacation_end_date")
    private Instant vacationEndDate;

    @Column("currency_type")
    private String currencyType;

    @Column("variable_remuneration")
    private String variableRemuneration;

    @Column("occupation_group_id")
    private String occupationGroupId;

    @Column("sub_modality_id")
    private String subModalityId;

    @Column("is_intern")
    private Boolean isIntern;

    @Column("documents_delivered")
    private Boolean documentsDelivered;

    @Column("fingerprint_registered")
    private Boolean fingerprintRegistered;

    @Column("payroll_registered")
    private Boolean payrollRegistered;

    @Column("company_ruc")
    private String companyRuc;

    @Column("branch_code")
    private String branchCode;

    @Column("special_situation_id")
    private String specialSituationId;

    @Column("special_situation_desc")
    private String specialSituationDesc;
}
