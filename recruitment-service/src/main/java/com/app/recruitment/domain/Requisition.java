package com.app.recruitment.domain;

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
@Table("requisition")
public class Requisition {

    @Id
    private UUID id;

    @Version
    private Long version;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("request_number")
    private String requestNumber;

    @Column("payroll_type_id")
    private String payrollTypeId;

    @Column("position_id")
    private String positionId;

    @Column("cost_center_id")
    private UUID costCenterId;

    @Column("start_date")
    private Instant startDate;

    @Column("end_date")
    private Instant endDate;

    @Column("salary_amount")
    private Double salaryAmount;

    @Column("food_bonus")
    private Double foodBonus;

    @Column("work_days")
    private String workDays;

    @Column("service_location")
    private String serviceLocation;

    @Column("service_description")
    private String serviceDescription;

    @Column("payment_period")
    private String paymentPeriod;

    @Column("fiscal_address")
    private String fiscalAddress;

    @Column("allowance_description")
    private String allowanceDescription;

    @Column("training_schedule")
    private String trainingSchedule;

    @Column("break_schedule")
    private String breakSchedule;

    @Column("training_days")
    private String trainingDays;

    @Column("police_record_desc")
    private String policeRecordDesc;

    @Column("health_certificate_desc")
    private String healthCertificateDesc;

    @Column("bank_name")
    private String bankName;

    @Column("bank_account")
    private String bankAccount;

    @Column("status")
    private String status;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;

    @Column("created_by")
    private String createdBy;
}
