package com.app.recruitment.persistence;

import com.app.recruitment.domain.University;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface UniversityRepository extends R2dbcRepository<University, UUID> {
}
