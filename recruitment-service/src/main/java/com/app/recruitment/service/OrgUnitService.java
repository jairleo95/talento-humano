package com.app.recruitment.service;

import com.app.recruitment.domain.OrganizationalUnit;
import com.app.recruitment.persistence.OrganizationalUnitRepository;
import com.app.recruitment.web.OrgUnitMapper;
import com.app.recruitment.web.dto.OrgUnitRequest;
import com.app.recruitment.web.dto.OrgUnitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrgUnitService {

    private static final String UNIT_NOT_FOUND = "Organizational unit not found: ";

    private final OrganizationalUnitRepository repository;
    private final OrgUnitMapper mapper;

    public Flux<OrgUnitResponse> listByType(String unitType) {
        Flux<OrganizationalUnit> flux = unitType == null || unitType.isBlank()
                ? repository.findAll()
                : repository.findByUnitTypeOrderByNameAsc(unitType);
        return flux.map(mapper::toResponse);
    }

    public Flux<OrgUnitResponse> listByParent(UUID parentId) {
        return repository.findByParentIdAndIsActiveTrueOrderByNameAsc(parentId)
                .map(mapper::toResponse);
    }

    public Mono<OrgUnitResponse> create(OrgUnitRequest request) {
        OrganizationalUnit entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return repository.save(entity).map(mapper::toResponse);
    }

    public Mono<OrgUnitResponse> update(UUID id, OrgUnitRequest request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(UNIT_NOT_FOUND + id)))
                .flatMap(unit -> {
                    unit.setName(request.name());
                    unit.setShortName(request.shortName());
                    unit.setUnitType(request.unitType());
                    unit.setParentId(request.parentId());
                    unit.setIsActive(request.isActive());
                    unit.setUpdatedAt(Instant.now());
                    return repository.save(unit).map(mapper::toResponse);
                });
    }

    public Mono<Void> toggleActive(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(UNIT_NOT_FOUND + id)))
                .flatMap(unit -> {
                    unit.setIsActive(!Boolean.TRUE.equals(unit.getIsActive()));
                    unit.setUpdatedAt(Instant.now());
                    return repository.save(unit);
                })
                .then();
    }
}
