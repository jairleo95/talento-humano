package com.app.recruitment.service;

import com.app.recruitment.domain.InboxItem;
import com.app.recruitment.persistence.InboxRepository;
import com.app.recruitment.persistence.ProcessStepRepository;
import com.app.recruitment.persistence.RequisitionRepository;
import com.app.recruitment.web.InboxMapper;
import com.app.recruitment.web.dto.InboxItemRequest;
import com.app.recruitment.web.dto.InboxItemResponse;
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
public class InboxService {

    private final InboxRepository repository;
    private final RequisitionRepository requisitionRepository;
    private final ProcessStepRepository processStepRepository;
    private final InboxMapper mapper;
    private final ReactiveTransactionManager transactionManager;

    public Flux<InboxItemResponse> list(String assignee) {
        Flux<InboxItem> data = assignee == null ? repository.findAll() : repository.findByAssignee(assignee);
        return data.map(mapper::toResponse);
    }

    public Mono<InboxItemResponse> assign(InboxItemRequest request) {
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);
        InboxItem entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());

        Mono<Void> ensureRefs = requisitionRepository.findById(request.requisitionId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Requisition not found: " + request.requisitionId())))
                .then(processStepRepository.findById(request.processStepId())
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Process step not found: " + request.processStepId()))))
                .then();

        return ensureRefs.then(repository.save(entity))
                .map(mapper::toResponse)
                .as(tx::transactional);
    }

    public Mono<InboxItemResponse> updateStatus(UUID id, String status) {
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Inbox item not found: " + id)))
                .flatMap(item -> {
                    item.setStatus(status);
                    item.setUpdatedAt(Instant.now());
                    return repository.save(item);
                })
                .map(mapper::toResponse)
                .as(tx::transactional);
    }
}
