package com.app.contract.persistence;

import com.app.contract.domain.ContractTemplate;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ContractTemplateRepository extends R2dbcRepository<ContractTemplate, UUID> {

    Flux<ContractTemplate> findByNameOrderByVersionDesc(String name);
}
