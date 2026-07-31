package com.app.recruitment.web;

import com.app.recruitment.domain.InboxItem;
import com.app.recruitment.web.dto.InboxItemRequest;
import com.app.recruitment.web.dto.InboxItemResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class InboxMapper {

    public InboxItem toEntity(InboxItemRequest request, UUID id) {
        Instant now = Instant.now();
        return InboxItem.builder()
                .id(id)
                .requisitionId(request.requisitionId())
                .processStepId(request.processStepId())
                .assignee(request.assignee())
                .status("PENDING")
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public InboxItemResponse toResponse(InboxItem item) {
        return new InboxItemResponse(
                item.getId(),
                item.getRequisitionId(),
                item.getProcessStepId(),
                item.getAssignee(),
                item.getStatus(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}
