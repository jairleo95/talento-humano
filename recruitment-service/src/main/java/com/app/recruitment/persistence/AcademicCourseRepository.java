package com.app.recruitment.persistence;

import com.app.recruitment.domain.AcademicCourse;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface AcademicCourseRepository extends R2dbcRepository<AcademicCourse, UUID> {

    Flux<AcademicCourse> findByChargeIdOrderByCourseNameAsc(UUID chargeId);
}