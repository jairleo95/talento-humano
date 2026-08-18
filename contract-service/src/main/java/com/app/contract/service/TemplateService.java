package com.app.contract.service;

import com.app.contract.domain.ContractTemplate;
import com.app.contract.persistence.ContractTemplateRepository;
import com.app.contract.web.TemplateMapper;
import com.app.contract.web.dto.TemplateRequest;
import com.app.contract.web.dto.TemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final ContractTemplateRepository repository;
    private final TemplateMapper mapper;

    public Flux<TemplateResponse> list(String name) {
        Flux<ContractTemplate> flux = name == null ? repository.findAll() : repository.findByNameOrderByVersionDesc(name);
        return flux.map(mapper::toResponse);
    }

    public Mono<TemplateResponse> getById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Plantilla no encontrada: " + id)))
                .map(mapper::toResponse);
    }

    public Mono<TemplateResponse> create(TemplateRequest request) {
        ContractTemplate entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setContent(HtmlSanitizer.sanitize(request.content()));
        return repository.save(entity).map(mapper::toResponse);
    }

    public Mono<TemplateResponse> update(UUID id, TemplateRequest request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Plantilla no encontrada: " + id)))
                .flatMap(t -> {
                    t.setName(request.name());
                    t.setVersion(request.version());
                    t.setContent(HtmlSanitizer.sanitize(request.content()));
                    t.setFileName(request.fileName());
                    t.setStatus(request.status());
                    return repository.save(t);
                })
                .map(mapper::toResponse);
    }

    public Mono<TemplateResponse> toggleStatus(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Plantilla no encontrada: " + id)))
                .flatMap(t -> {
                    t.setStatus("ACTIVE".equals(t.getStatus()) ? "INACTIVE" : "ACTIVE");
                    return repository.save(t);
                })
                .map(mapper::toResponse);
    }

    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
    }
}
