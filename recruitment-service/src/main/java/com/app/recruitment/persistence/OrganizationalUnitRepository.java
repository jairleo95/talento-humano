package com.app.recruitment.persistence;

import com.app.recruitment.domain.OrganizationalUnit;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface OrganizationalUnitRepository extends R2dbcRepository<OrganizationalUnit, UUID> {

    Flux<OrganizationalUnit> findByUnitTypeOrderByNameAsc(String unitType);

    Flux<OrganizationalUnit> findByParentIdAndIsActiveTrueOrderByNameAsc(UUID parentId);
}
