package com.app.recruitment.web;

import com.app.recruitment.service.RequisitionService;
import com.app.recruitment.web.dto.RequisitionRequest;
import com.app.recruitment.web.dto.RequisitionResponse;
import com.app.recruitment.web.dto.RequisitionStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recruitment/requisitions")
@RequiredArgsConstructor
public class RequisitionController {

    private final RequisitionService service;

    @GetMapping
    public Flux<RequisitionResponse> list(@RequestParam(name = "status", required = false) String status) {
        return service.list(status);
    }

    @GetMapping("/{id}")
    public Mono<RequisitionResponse> findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RequisitionResponse> create(@RequestBody @Validated RequisitionRequest request) {
        return service.create(request);
    }

    @PatchMapping("/{id}/status")
    public Mono<RequisitionResponse> updateStatus(@PathVariable UUID id,
                                                  @RequestBody @Validated RequisitionStatusRequest request) {
        return service.updateStatus(id, request.status());
    }
}
