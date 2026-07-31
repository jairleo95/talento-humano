package com.app.recruitment.web;

import com.app.recruitment.domain.Process;
import com.app.recruitment.domain.ProcessStep;
import com.app.recruitment.web.dto.ProcessRequest;
import com.app.recruitment.web.dto.ProcessResponse;
import com.app.recruitment.web.dto.ProcessStepRequest;
import com.app.recruitment.web.dto.ProcessStepResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = { Instant.class })
public interface ProcessMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    Process toEntity(ProcessRequest request);

    ProcessResponse toResponse(Process process);

    default ProcessStep toStep(ProcessStepRequest request, UUID processId) {
        ProcessStep step = new ProcessStep();
        step.setId(UUID.randomUUID());
        step.setProcessId(processId);
        step.setName(request.name());
        step.setCode(request.code());
        step.setDescription(request.description());
        step.setStatus("PENDING");
        step.setOrderIndex(request.orderIndex());
        step.setSlaHours(request.slaHours());
        step.setCreatedAt(Instant.now());
        step.setUpdatedAt(Instant.now());
        return step;
    }

    ProcessStepResponse toStepResponse(ProcessStep step);
}
