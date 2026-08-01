package com.app.recruitment.persistence;
import com.app.recruitment.domain.BudgetPeriod;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;
public interface BudgetPeriodRepository extends R2dbcRepository<BudgetPeriod, UUID> {
    Flux<BudgetPeriod> findAllByOrderByCreatedAtDesc();
}