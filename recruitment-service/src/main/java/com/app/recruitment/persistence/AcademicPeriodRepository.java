package com.app.recruitment.persistence;

import com.app.recruitment.domain.AcademicPeriod;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AcademicPeriodRepository extends R2dbcRepository<AcademicPeriod, UUID> {

    Flux<AcademicPeriod> findAllByOrderByStartDateDesc();

    Mono<Boolean> existsByCode(String code);
}
