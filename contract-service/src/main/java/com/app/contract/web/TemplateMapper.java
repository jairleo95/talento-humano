package com.app.contract.web;

import com.app.contract.domain.ContractTemplate;
import com.app.contract.web.dto.TemplateRequest;
import com.app.contract.web.dto.TemplateResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class TemplateMapper {

    public ContractTemplate toEntity(TemplateRequest request, UUID id) {
        return ContractTemplate.builder()
                .id(id)
                .name(request.name())
                .version(request.version())
                .content(request.content())
                .fileName(request.fileName())
                .status(request.status() == null ? "ACTIVE" : request.status())
                .createdAt(Instant.now())
                .createdBy(request.createdBy())
                .build();
    }

    public TemplateResponse toResponse(ContractTemplate entity) {
        return new TemplateResponse(
                entity.getId(),
                entity.getName(),
                entity.getVersion(),
                entity.getContent(),
                entity.getFileName(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getCreatedBy()
        );
    }
}
