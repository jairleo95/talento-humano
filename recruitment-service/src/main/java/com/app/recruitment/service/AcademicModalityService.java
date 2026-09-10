package com.app.recruitment.service;

import com.app.recruitment.domain.AcademicModality;
import com.app.recruitment.persistence.AcademicModalityRepository;
import com.app.recruitment.web.AcademicModalityMapper;
import com.app.recruitment.web.dto.AcademicModalityRequest;
import com.app.recruitment.web.dto.AcademicModalityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AcademicModalityService {

    private final AcademicModalityRepository repository;
    private final AcademicModalityMapper mapper;

    public Flux<AcademicModalityResponse> list() {
        return repository.findAllByOrderBySortOrderAsc().map(mapper::toResponse);
    }

    public Mono<AcademicModalityResponse> create(AcademicModalityRequest request) {
        return repository.existsByCode(request.code())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una modalidad con el código " + request.code()));
                    }
                    AcademicModality entity = mapper.toEntity(request);
                    entity.setId(UUID.randomUUID());
                    return repository.save(entity);
                })
                .map(mapper::toResponse);
    }

    public Mono<AcademicModalityResponse> toggle(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Modalidad no encontrada con id: " + id)))
                .flatMap(entity -> {
                    entity.setIsActive(!Boolean.TRUE.equals(entity.getIsActive()));
                    return repository.save(entity);
                })
                .map(mapper::toResponse);
    }
}
