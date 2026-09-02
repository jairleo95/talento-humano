package com.app.recruitment.web;

import com.app.recruitment.service.AcademicChargeService;
import com.app.recruitment.web.dto.AcademicChargeRequest;
import com.app.recruitment.web.dto.AcademicChargeResponse;
import com.app.recruitment.web.dto.AcademicChargeStatusRequest;
import com.app.recruitment.web.dto.AcademicPaymentResponse;
import com.app.recruitment.web.dto.AcademicPaymentStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recruitment/academic-charges")
@RequiredArgsConstructor
public class AcademicChargeController {

    private final AcademicChargeService service;

    @GetMapping
    public Flux<AcademicChargeResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public Mono<AcademicChargeResponse> findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AcademicChargeResponse> create(@RequestBody @Validated AcademicChargeRequest request) {
        return service.create(request);
    }

    @PatchMapping("/{id}/status")
    public Mono<AcademicChargeResponse> updateStatus(@PathVariable UUID id,
                                                     @RequestBody @Validated AcademicChargeStatusRequest request) {
        return service.updateStatus(id, request);
    }

    @PatchMapping("/{chargeId}/payments/{paymentId}/status")
    public Mono<AcademicPaymentResponse> updatePaymentStatus(@PathVariable UUID chargeId,
                                                             @PathVariable UUID paymentId,
                                                             @RequestBody @Validated AcademicPaymentStatusRequest request) {
        return service.updatePaymentStatus(chargeId, paymentId, request);
    }
}