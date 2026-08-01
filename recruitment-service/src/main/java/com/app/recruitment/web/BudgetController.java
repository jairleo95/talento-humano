package com.app.recruitment.web;

import com.app.recruitment.domain.BudgetAllocation;
import com.app.recruitment.domain.BudgetPeriod;
import com.app.recruitment.service.BudgetService;
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
    public Mono<BudgetPeriod> createPeriod(@RequestBody BudgetPeriod period) { return service.createPeriod(period); }

    @GetMapping("/periods/{periodId}/allocations")
    public Flux<BudgetAllocation> listAllocations(@PathVariable UUID periodId) { return service.listAllocations(periodId); }

    @PostMapping("/periods/{periodId}/allocations")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BudgetAllocation> createAllocation(@PathVariable UUID periodId, @RequestBody BudgetAllocation alloc) {
        alloc.setPeriodId(periodId);
        return service.createAllocation(alloc);
    }
}
