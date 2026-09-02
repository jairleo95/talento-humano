package com.app.recruitment.persistence;

import com.app.recruitment.domain.AcademicPayment;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface AcademicPaymentRepository extends R2dbcRepository<AcademicPayment, UUID> {

    Flux<AcademicPayment> findByChargeIdOrderByQuotaNumberAsc(UUID chargeId);
}