package com.app.contract.service;

import com.app.contract.domain.Contract;
import com.app.contract.persistence.ContractRepository;
import com.app.contract.util.UrlValidator;
import com.app.contract.web.ContractMapper;
import com.app.contract.web.dto.ContractRequest;
import com.app.contract.web.dto.ContractResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository repository;
    private final ContractMapper mapper;
    private final ReactiveTransactionManager transactionManager;

    @Scheduled(cron = "0 0 0 * * ?")
    public void expireOutdatedContracts() {
        repository.findByStatusAndEndDateBefore("ACTIVE", Instant.now())
                .flatMap(c -> {
                    c.setStatus("EXPIRED");
                    c.setUpdatedAt(Instant.now());
                    return repository.save(c);
                })
                .doOnError(error -> System.err.println("Error expiring outdated contracts: " + error.getMessage()))
                .subscribe();
    }

    public Flux<ContractResponse> list(UUID requisitionId) {
        Flux<Contract> flux = requisitionId == null ? repository.findAll() : repository.findByRequisitionId(requisitionId);
        return flux.map(mapper::toResponse);
    }

    public Mono<ContractResponse> create(ContractRequest request) {
        Contract entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        if (Boolean.TRUE.equals(request.isSpecialCase())) {
            entity.setIsSpecialCase(true);
        }
        return repository.save(entity).map(mapper::toResponse);
    }

    public Mono<ContractResponse> sign(UUID id) {
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Contract not found: " + id)))
                .flatMap(c -> {
                    c.setStatus("ACTIVE");
                    c.setSignedAt(Instant.now());
                    c.setUpdatedAt(Instant.now());
                    return repository.save(c);
                })
                .map(mapper::toResponse)
                .as(tx::transactional);
    }

    public Mono<ContractResponse> update(UUID id, ContractRequest request) {
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Contract not found: " + id)))
                .flatMap(c -> {
                    c.setContractNumber(request.contractNumber());
                    c.setWorkerId(request.workerId());
                    c.setPositionId(request.positionId());
                    c.setStartDate(request.startDate());
                    c.setEndDate(request.endDate());
                    c.setTerminationDate(request.terminationDate());
                    c.setConditionType(request.conditionType());
                    c.setSalaryAmount(request.salaryAmount());
                    c.setReintegrationAmount(request.reintegrationAmount());
                    c.setFamilyAllowance(request.familyAllowance());
                    c.setWeeklyHours(request.weeklyHours());
                    c.setDailyHours(request.dailyHours());
                    c.setLaborRegime(request.laborRegime());
                    c.setPensionRegime(request.pensionRegime());
                    c.setContractType(request.contractType());
                    c.setObservation(request.observation());
                    c.setParentContractId(request.parentContractId());
                    c.setIsSpecialCase(request.isSpecialCase());
                    c.setUpdatedAt(Instant.now());
                    c.setUpdatedBy(request.createdBy());
                    return repository.save(c);
                })
                .map(mapper::toResponse)
                .as(tx::transactional);
    }

    public Flux<ContractResponse> getWorkerHistory(String workerId) {
        return repository.findByWorkerIdOrderByStartDateAsc(workerId).map(mapper::toResponse);
    }

    public Mono<ContractResponse> uploadSignedDocument(UUID id, String fileUrl, String signedBy) {
        if (!UrlValidator.isSafeUrl(fileUrl)) {
            return Mono.error(new IllegalArgumentException("Invalid fileUrl: only http/https URLs are allowed"));
        }
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);
        return repository.findById(id)
                .flatMap(c -> {
                    c.setSignedFileUrl(fileUrl);
                    c.setSignedBy(signedBy);
                    c.setSignedAt(Instant.now());
                    c.setStatus("SIGNED");
                    c.setUpdatedAt(Instant.now());
                    return repository.save(c);
                })
                .map(mapper::toResponse)
                .as(tx::transactional);
    }
}
