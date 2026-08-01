package com.app.recruitment.service;

import com.app.recruitment.domain.University;
import com.app.recruitment.persistence.UniversityRepository;
import com.app.recruitment.web.UniversityMapper;
import com.app.recruitment.web.dto.UniversityRequest;
import com.app.recruitment.web.dto.UniversityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository repository;
    private final UniversityMapper mapper;

    public Flux<UniversityResponse> list() {
        return repository.findAll().map(mapper::toResponse);
    }

    public Mono<UniversityResponse> create(UniversityRequest request) {
        University entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return repository.save(entity).map(mapper::toResponse);
    }
}
