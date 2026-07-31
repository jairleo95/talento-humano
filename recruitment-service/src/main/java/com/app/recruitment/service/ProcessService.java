package com.app.recruitment.service;

import com.app.recruitment.domain.Process;
import com.app.recruitment.domain.ProcessStep;
import com.app.recruitment.persistence.ProcessRepository;
import com.app.recruitment.persistence.ProcessStepRepository;
import com.app.recruitment.web.ProcessMapper;
import com.app.recruitment.web.dto.ProcessRequest;
import com.app.recruitment.web.dto.ProcessResponse;
import com.app.recruitment.web.dto.ProcessStepRequest;
import com.app.recruitment.web.dto.ProcessStepResponse;
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
public class ProcessService {

    private final ProcessRepository processRepository;
    private final ProcessStepRepository stepRepository;
    private final ProcessMapper mapper;
    private final ReactiveTransactionManager transactionManager;

    public Flux<ProcessResponse> list(String status) {
        Flux<Process> flux = status == null ? processRepository.findAll() : processRepository.findByStatus(status);
        return flux.map(mapper::toResponse);
    }

    public Mono<ProcessResponse> create(ProcessRequest request) {
        Process process = mapper.toEntity(request);
        process.setId(UUID.randomUUID());
        return processRepository.save(process).map(mapper::toResponse);
    }

    public Mono<ProcessStepResponse> addStep(UUID processId, ProcessStepRequest request) {
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);
        return processRepository.findById(processId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Process not found: " + processId)))
                .flatMap(p -> {
                    ProcessStep step = mapper.toStep(request, processId);
                    return stepRepository.save(step);
                })
                .map(mapper::toStepResponse)
                .as(tx::transactional);
    }

    public Flux<ProcessStepResponse> steps(UUID processId) {
        return stepRepository.findByProcessIdOrderByOrderIndex(processId)
                .map(mapper::toStepResponse);
    }

    public Mono<ProcessResponse> updateStatus(UUID processId, String status) {
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);
        return processRepository.findById(processId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Process not found: " + processId)))
                .flatMap(p -> {
                    p.setStatus(status);
                    p.setUpdatedAt(Instant.now());
                    return processRepository.save(p);
                })
                .map(mapper::toResponse)
                .as(tx::transactional);
    }
}
