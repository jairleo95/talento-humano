package com.app.recruitment.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AcademicPaymentRequest(
        @NotNull Integer quotaNumber,
        @NotNull @Min(0) Double amount,
        LocalDate paymentDate
) {
}