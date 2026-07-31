package com.app.recruitment.web;

import com.app.recruitment.domain.Process;
import com.app.recruitment.domain.ProcessStep;
import com.app.recruitment.web.dto.ProcessRequest;
import com.app.recruitment.web.dto.ProcessResponse;
import com.app.recruitment.web.dto.ProcessStepRequest;
import com.app.recruitment.web.dto.ProcessStepResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class ProcessMapper {

    public Process toEntity(ProcessRequest request, UUID id) {
        Instant now = Instant.now();
        return Process.builder()
                .id(id)
                .name(request.name())
                .code(request.code())
                .description(request.description())
                .status("ACTIVE")
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public ProcessResponse toResponse(Process process) {
        return new ProcessResponse(
                process.getId(),
                process.getName(),
                process.getCode(),
                process.getDescription(),
                process.getStatus(),
                process.getCreatedAt(),
                process.getUpdatedAt()
        );
    }

    public ProcessStep toStep(ProcessStepRequest request, UUID processId, UUID id) {
        Instant now = Instant.now();
        return ProcessStep.builder()
                .id(id)
                .processId(processId)
                .name(request.name())
                .code(request.code())
                .description(request.description())
                .status("PENDING")
                .orderIndex(request.orderIndex())
                .slaHours(request.slaHours())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public ProcessStepResponse toStepResponse(ProcessStep step) {
        return new ProcessStepResponse(
                step.getId(),
                step.getProcessId(),
                step.getName(),
                step.getCode(),
                step.getDescription(),
                step.getStatus(),
                step.getOrderIndex(),
                step.getSlaHours(),
                step.getCreatedAt(),
                step.getUpdatedAt()
        );
    }
}
