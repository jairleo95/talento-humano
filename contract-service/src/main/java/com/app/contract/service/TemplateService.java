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

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final ContractTemplateRepository repository;
    private final TemplateMapper mapper;

    public Flux<TemplateResponse> list(String name) {
        Flux<ContractTemplate> flux = name == null ? repository.findAll() : repository.findByNameOrderByVersionDesc(name);
        return flux.map(mapper::toResponse);
    }

    public Mono<TemplateResponse> create(TemplateRequest request) {
        ContractTemplate entity = mapper.toEntity(request, UUID.randomUUID());
        return repository.save(entity).map(mapper::toResponse);
    }
}
