package com.app.recruitment.persistence;
import com.app.recruitment.domain.DgpComment;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;
public interface DgpCommentRepository extends R2dbcRepository<DgpComment, UUID> {
    Flux<DgpComment> findByRequisitionIdOrderByCreatedAtDesc(UUID requisitionId);
}