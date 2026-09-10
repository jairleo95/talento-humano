package com.app.recruitment.web;

import com.app.recruitment.service.AcademicPeriodService;
import com.app.recruitment.web.dto.AcademicPeriodRequest;
import com.app.recruitment.web.dto.AcademicPeriodResponse;
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
@RequestMapping("/api/v1/recruitment/academic/periods")
@RequiredArgsConstructor
public class AcademicPeriodController {

    private final AcademicPeriodService service;

    @GetMapping
    public Flux<AcademicPeriodResponse> list() {
        return service.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AcademicPeriodResponse> create(@RequestBody @Validated AcademicPeriodRequest request) {
        return service.create(request);
    }

    @PatchMapping("/{id}/toggle")
    public Mono<AcademicPeriodResponse> toggle(@PathVariable UUID id) {
        return service.toggle(id);
    }
}
