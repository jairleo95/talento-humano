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
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("academic_charge")
public class AcademicCharge {

    @Id
    private UUID id;

    @Version
    private Long version;

    @Column("worker_id")
    private UUID workerId;

    @Column("semester")
    private String semester;

    @Column("faculty")
    private String faculty;

    @Column("school")
    private String school;

    @Column("educational_situation")
    private String educationalSituation;

    @Column("profession")
    private String profession;

    @Column("condicion")
    private String condition;

    @Column("pay_type")
    private String payType;

    @Column("total_hours")
    private Double totalHours;

    @Column("start_date")
    private LocalDate startDate;

    @Column("end_date")
    private LocalDate endDate;

    @Column("status")
    private String status;

    @Column("created_by")
    private String createdBy;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;
}