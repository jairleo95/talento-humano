package com.app.recruitment.persistence;

import com.app.recruitment.domain.ProcessStep;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ProcessStepRepository extends R2dbcRepository<ProcessStep, UUID> {

    Flux<ProcessStep> findByProcessIdOrderByOrderIndex(UUID processId);
}
