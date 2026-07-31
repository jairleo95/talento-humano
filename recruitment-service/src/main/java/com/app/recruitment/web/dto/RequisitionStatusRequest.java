package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;

public record RequisitionStatusRequest(@NotBlank String status) {
}
