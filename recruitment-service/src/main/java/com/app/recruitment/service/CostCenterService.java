package com.app.recruitment.service;

import com.app.recruitment.domain.CostCenter;
import com.app.recruitment.persistence.CostCenterRepository;
import com.app.recruitment.web.CostCenterMapper;
import com.app.recruitment.web.dto.CostCenterRequest;
import com.app.recruitment.web.dto.CostCenterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CostCenterService {

    private final CostCenterRepository repository;
    private final CostCenterMapper mapper;

    public Flux<CostCenterResponse> list() {
        return repository.findAll().map(mapper::toResponse);
    }

    public Mono<CostCenterResponse> create(CostCenterRequest request) {
        UUID id = UUID.randomUUID();
        CostCenter entity = mapper.toEntity(request, id);
        return repository.existsByCode(request.code())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new DuplicateKeyException("Cost center already exists with code: " + request.code()));
                    }
                    return repository.save(entity).map(mapper::toResponse);
                });
    }
}
