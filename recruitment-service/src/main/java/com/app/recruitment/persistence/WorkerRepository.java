package com.app.recruitment.persistence;

import com.app.recruitment.domain.Worker;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface WorkerRepository extends R2dbcRepository<Worker, UUID> {

    @Query("""
        SELECT * FROM worker
        WHERE LOWER(first_name) LIKE LOWER('%' || :term || '%')
           OR LOWER(last_name_paternal) LIKE LOWER('%' || :term || '%')
           OR LOWER(last_name_maternal) LIKE LOWER('%' || :term || '%')
           OR LOWER(document_number) LIKE LOWER('%' || :term || '%')
    """)
    Flux<Worker> searchByTerm(String term);
}
