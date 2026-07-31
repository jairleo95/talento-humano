package com.app.contract.persistence;

import com.app.contract.domain.ContractAttachment;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ContractAttachmentRepository extends R2dbcRepository<ContractAttachment, UUID> {

    Flux<ContractAttachment> findByContractId(UUID contractId);
}
