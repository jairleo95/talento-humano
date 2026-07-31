package com.app.contract.web;

import com.app.contract.domain.ContractAttachment;
import com.app.contract.web.dto.AttachmentRequest;
import com.app.contract.web.dto.AttachmentResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class AttachmentMapper {

    public ContractAttachment toEntity(AttachmentRequest request, UUID id) {
        return ContractAttachment.builder()
                .id(id)
                .contractId(request.contractId())
                .filename(request.filename())
                .contentType(request.contentType())
                .uri(request.uri())
                .sizeBytes(request.sizeBytes())
                .checksum(request.checksum())
                .createdAt(Instant.now())
                .build();
    }

    public AttachmentResponse toResponse(ContractAttachment entity) {
        return new AttachmentResponse(
                entity.getId(),
                entity.getContractId(),
                entity.getFilename(),
                entity.getContentType(),
                entity.getUri(),
                entity.getSizeBytes(),
                entity.getChecksum(),
                entity.getCreatedAt()
        );
    }
}
