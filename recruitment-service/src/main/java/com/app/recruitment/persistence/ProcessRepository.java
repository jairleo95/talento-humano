package com.app.recruitment.persistence;

import com.app.recruitment.domain.Process;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ProcessRepository extends R2dbcRepository<Process, UUID> {

    Flux<Process> findByStatus(String status);
}
