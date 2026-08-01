package com.app.recruitment.persistence;
import com.app.recruitment.domain.BudgetAllocation;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;
public interface BudgetAllocationRepository extends R2dbcRepository<BudgetAllocation, UUID> {
    Flux<BudgetAllocation> findByPeriodIdOrderByCreatedAtDesc(UUID periodId);
}