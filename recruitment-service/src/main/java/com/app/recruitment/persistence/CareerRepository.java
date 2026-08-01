package com.app.recruitment.persistence;

import com.app.recruitment.domain.Career;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface CareerRepository extends R2dbcRepository<Career, UUID> {
}
