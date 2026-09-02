package com.app.recruitment.persistence;

import com.app.recruitment.domain.AcademicCharge;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface AcademicChargeRepository extends R2dbcRepository<AcademicCharge, UUID> {
}