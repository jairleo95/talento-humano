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
@Table("academic_period")
public class AcademicPeriod {

    @Id
    private UUID id;

    @Version
    private Long version;

    @Column("code")
    private String code;

    @Column("name")
    private String name;

    @Column("start_date")
    private LocalDate startDate;

    @Column("end_date")
    private LocalDate endDate;

    @Column("is_active")
    private Boolean isActive;

    @Column("created_at")
    private Instant createdAt;
}
