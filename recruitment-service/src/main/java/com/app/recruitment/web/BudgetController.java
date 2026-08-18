package com.app.recruitment.web;

import com.app.recruitment.domain.BudgetAllocation;
import com.app.recruitment.domain.BudgetPeriod;
import com.app.recruitment.service.BudgetService;
import com.app.recruitment.web.dto.BudgetAllocationRequest;
import com.app.recruitment.web.dto.BudgetPeriodRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recruitment/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService service;

    @GetMapping("/periods")
    public Flux<BudgetPeriod> listPeriods() { return service.listPeriods(); }

    @PostMapping("/periods")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BudgetPeriod> createPeriod(@RequestBody @Valid BudgetPeriodRequest request) {
        BudgetPeriod period = new BudgetPeriod();
        period.setName(request.name());
        period.setStartDate(request.startDate());
        period.setEndDate(request.endDate());
        return service.createPeriod(period);
    }

    @GetMapping("/periods/{periodId}/allocations")
    public Flux<BudgetAllocation> listAllocations(@PathVariable UUID periodId) { return service.listAllocations(periodId); }

    @PostMapping("/periods/{periodId}/allocations")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BudgetAllocation> createAllocation(@PathVariable UUID periodId, @RequestBody @Valid BudgetAllocationRequest request) {
        BudgetAllocation alloc = new BudgetAllocation();
        alloc.setPeriodId(periodId);
        alloc.setRequirementType(request.requirementType());
        alloc.setPositionId(request.positionId());
        alloc.setWorkerCount(request.workerCount());
        alloc.setMinSalary(request.minSalary());
        alloc.setMaxSalary(request.maxSalary());
        alloc.setMinBonus(request.minBonus());
        alloc.setMaxBonus(request.maxBonus());
        alloc.setMinFoodBonus(request.minFoodBonus());
        alloc.setMaxFoodBonus(request.maxFoodBonus());
        return service.createAllocation(alloc);
    }
}
