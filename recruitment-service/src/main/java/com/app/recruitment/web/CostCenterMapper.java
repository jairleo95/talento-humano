package com.app.recruitment.web;

import com.app.recruitment.domain.CostCenter;
import com.app.recruitment.web.dto.CostCenterRequest;
import com.app.recruitment.web.dto.CostCenterResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class CostCenterMapper {

    public CostCenter toEntity(CostCenterRequest request, UUID id) {
        return CostCenter.builder()
                .id(id)
                .code(request.code())
                .name(request.name())
                .departmentId(request.departmentId())
                .percentage(request.percentage())
                .createdAt(Instant.now())
                .build();
    }

    public CostCenterResponse toResponse(CostCenter entity) {
        return new CostCenterResponse(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDepartmentId(),
                entity.getPercentage(),
                entity.getCreatedAt()
        );
    }
}
