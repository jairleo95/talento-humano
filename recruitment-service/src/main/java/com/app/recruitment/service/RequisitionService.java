package com.app.recruitment.service;

import com.app.recruitment.domain.Requisition;
import com.app.recruitment.persistence.RequisitionRepository;
import com.app.recruitment.web.RequisitionMapper;
import com.app.recruitment.web.dto.RequisitionRequest;
import com.app.recruitment.web.dto.RequisitionResponse;
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
public class RequisitionService {

    private final RequisitionRepository repository;
    private final RequisitionMapper mapper;
    private final ReactiveTransactionManager transactionManager;

    public Flux<RequisitionResponse> list(String status) {
        Flux<Requisition> data = status == null ? repository.findAll() : repository.findByStatus(status);
        return data.map(mapper::toResponse);
    }

    public Mono<RequisitionResponse> findById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Requisition not found: " + id)))
                .map(mapper::toResponse);
    }

    public Mono<RequisitionResponse> create(RequisitionRequest request) {
        Requisition entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return repository.save(entity).map(mapper::toResponse);
    }

    public Mono<RequisitionResponse> updateStatus(UUID id, String status) {
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Requisition not found: " + id)))
                .flatMap(req -> {
                    req.setStatus(status);
                    req.setUpdatedAt(Instant.now());
                    return repository.save(req);
                })
                .map(mapper::toResponse)
                .as(tx::transactional);
    }
}
