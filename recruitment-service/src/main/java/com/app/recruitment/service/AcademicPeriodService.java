package com.app.recruitment.service;

import com.app.recruitment.domain.AcademicPeriod;
import com.app.recruitment.persistence.AcademicPeriodRepository;
import com.app.recruitment.web.AcademicPeriodMapper;
import com.app.recruitment.web.dto.AcademicPeriodRequest;
import com.app.recruitment.web.dto.AcademicPeriodResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AcademicPeriodService {

    private final AcademicPeriodRepository repository;
    private final AcademicPeriodMapper mapper;

    public Flux<AcademicPeriodResponse> list() {
        return repository.findAllByOrderByStartDateDesc().map(mapper::toResponse);
    }

    public Mono<AcademicPeriodResponse> create(AcademicPeriodRequest request) {
        if (request.startDate() != null && request.endDate() != null && request.startDate().isAfter(request.endDate())) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de inicio no puede ser posterior a la fecha de fin"));
        }
        return repository.existsByCode(request.code())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un período académico con el código " + request.code()));
                    }
                    AcademicPeriod entity = mapper.toEntity(request);
                    entity.setId(UUID.randomUUID());
                    return repository.save(entity);
                })
                .map(mapper::toResponse);
    }

    public Mono<AcademicPeriodResponse> toggle(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Período académico no encontrado con id: " + id)))
                .flatMap(entity -> {
                    entity.setIsActive(!Boolean.TRUE.equals(entity.getIsActive()));
                    return repository.save(entity);
                })
                .map(mapper::toResponse);
    }
}
