package com.app.recruitment.service;

import com.app.recruitment.domain.BudgetAllocation;
import com.app.recruitment.domain.BudgetPeriod;
import com.app.recruitment.persistence.BudgetAllocationRepository;
import com.app.recruitment.persistence.BudgetPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetPeriodRepository periodRepo;
    private final BudgetAllocationRepository allocRepo;

    public Flux<BudgetPeriod> listPeriods() {
        return periodRepo.findAllByOrderByCreatedAtDesc();
    }

    public Mono<BudgetPeriod> createPeriod(BudgetPeriod period) {
        period.setId(UUID.randomUUID());
        period.setCreatedAt(Instant.now());
        return periodRepo.save(period);
    }

    public Flux<BudgetAllocation> listAllocations(UUID periodId) {
        return allocRepo.findByPeriodIdOrderByCreatedAtDesc(periodId);
    }

    public Mono<BudgetAllocation> createAllocation(BudgetAllocation alloc) {
        alloc.setId(UUID.randomUUID());
        alloc.setCreatedAt(Instant.now());
        return allocRepo.save(alloc);
    }
}
