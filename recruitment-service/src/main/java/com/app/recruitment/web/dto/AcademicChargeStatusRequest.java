package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotNull;

public record AcademicChargeStatusRequest(@NotNull String status) {
}