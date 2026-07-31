package com.app.recruitment.persistence;

import com.app.recruitment.domain.CostCenter;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CostCenterRepository extends R2dbcRepository<CostCenter, UUID> {

    Mono<Boolean> existsByCode(String code);
}
