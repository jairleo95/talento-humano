package com.app.recruitment.web.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AcademicPaymentResponse(
        UUID id,
        UUID chargeId,
        Integer quotaNumber,
        Double amount,
        LocalDate paymentDate,
        String status
) {
}