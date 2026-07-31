package com.app.recruitment.persistence;

import com.app.recruitment.domain.InboxItem;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface InboxRepository extends R2dbcRepository<InboxItem, UUID> {

    Flux<InboxItem> findByAssignee(String assignee);
}
