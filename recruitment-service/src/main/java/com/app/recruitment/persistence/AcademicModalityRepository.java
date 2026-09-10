package com.app.recruitment.persistence;

import com.app.recruitment.domain.AcademicModality;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AcademicModalityRepository extends R2dbcRepository<AcademicModality, UUID> {

    Flux<AcademicModality> findAllByOrderBySortOrderAsc();

    Mono<Boolean> existsByCode(String code);
}
