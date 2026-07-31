package com.app.recruitment.persistence;

import com.app.recruitment.domain.Requisition;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface RequisitionRepository extends R2dbcRepository<Requisition, UUID> {

    Flux<Requisition> findByStatus(String status);
}
