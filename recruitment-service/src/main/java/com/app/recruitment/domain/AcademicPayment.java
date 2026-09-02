package com.app.recruitment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("academic_payment")
public class AcademicPayment {

    @Id
    private UUID id;

    @Version
    private Long version;

    @Column("charge_id")
    private UUID chargeId;

    @Column("quota_number")
    private Integer quotaNumber;

    @Column("amount")
    private Double amount;

    @Column("payment_date")
    private LocalDate paymentDate;

    @Column("status")
    private String status;
}