package com.app.recruitment.persistence;
import com.app.recruitment.domain.DgpDocument;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;
public interface DgpDocumentRepository extends R2dbcRepository<DgpDocument, UUID> {
    Flux<DgpDocument> findByRequisitionIdOrderByCreatedAtDesc(UUID requisitionId);
}