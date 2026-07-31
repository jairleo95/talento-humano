package com.app.contract.service;

import com.app.contract.domain.Contract;
import com.app.contract.persistence.ContractRepository;
import com.app.contract.web.ContractMapper;
import com.app.contract.web.dto.ContractRequest;
import com.app.contract.web.dto.ContractResponse;
import lombok.RequiredArgsConstructor;
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

    public Flux<ContractResponse> list(UUID requisitionId) {
        Flux<Contract> flux = requisitionId == null ? repository.findAll() : repository.findByRequisitionId(requisitionId);
        return flux.map(mapper::toResponse);
    }

    public Mono<ContractResponse> create(ContractRequest request) {
        Contract entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return repository.save(entity).map(mapper::toResponse);
    }

    public Mono<ContractResponse> sign(UUID id) {
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Contract not found: " + id)))
                .flatMap(c -> {
                    c.setStatus("SIGNED");
                    c.setSignedAt(Instant.now());
                    c.setUpdatedAt(Instant.now());
                    return repository.save(c);
                })
                .map(mapper::toResponse)
                .as(tx::transactional);
    }
}
