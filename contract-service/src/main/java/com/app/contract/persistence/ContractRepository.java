package com.app.contract.persistence;

import com.app.contract.domain.Contract;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.UUID;

public interface ContractRepository extends R2dbcRepository<Contract, UUID> {

    Flux<Contract> findByRequisitionId(UUID requisitionId);

    Flux<Contract> findByStatusAndEndDateBefore(String status, Instant date);

    Flux<Contract> findByWorkerIdOrderByStartDateAsc(String workerId);
}
