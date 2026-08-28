package com.app.contract.service;

import com.app.contract.domain.ContractAttachment;
import com.app.contract.persistence.ContractAttachmentRepository;
import com.app.contract.persistence.ContractRepository;
import com.app.contract.util.UrlValidator;
import com.app.contract.web.AttachmentMapper;
import com.app.contract.web.dto.AttachmentRequest;
import com.app.contract.web.dto.AttachmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final ContractAttachmentRepository repository;
    private final ContractRepository contractRepository;
    private final AttachmentMapper mapper;
    private final ReactiveTransactionManager transactionManager;

    public Flux<AttachmentResponse> list(UUID contractId) {
        Flux<ContractAttachment> flux = contractId == null ? repository.findAll() : repository.findByContractId(contractId);
        return flux.map(mapper::toResponse);
    }

    public Mono<AttachmentResponse> create(AttachmentRequest request) {
        if (!UrlValidator.isSafeUrl(request.uri())) {
            return Mono.error(new IllegalArgumentException("Invalid uri: only http/https URLs are allowed"));
        }
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);
        ContractAttachment entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());

        return contractRepository.findById(request.contractId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Contract not found: " + request.contractId())))
                .then(repository.save(entity))
                .map(mapper::toResponse)
                .as(tx::transactional);
    }
}
