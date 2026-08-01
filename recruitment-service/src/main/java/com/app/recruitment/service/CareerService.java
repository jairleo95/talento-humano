package com.app.recruitment.service;

import com.app.recruitment.domain.Career;
import com.app.recruitment.persistence.CareerRepository;
import com.app.recruitment.web.CareerMapper;
import com.app.recruitment.web.dto.CareerRequest;
import com.app.recruitment.web.dto.CareerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CareerService {

    private final CareerRepository repository;
    private final CareerMapper mapper;

    public Flux<CareerResponse> list() {
        return repository.findAll().map(mapper::toResponse);
    }

    public Mono<CareerResponse> create(CareerRequest request) {
        Career entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return repository.save(entity).map(mapper::toResponse);
    }
}
