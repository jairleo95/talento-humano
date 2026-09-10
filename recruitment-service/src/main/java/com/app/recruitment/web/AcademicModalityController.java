package com.app.recruitment.web;

import com.app.recruitment.service.AcademicModalityService;
import com.app.recruitment.web.dto.AcademicModalityRequest;
import com.app.recruitment.web.dto.AcademicModalityResponse;
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
@RequestMapping("/api/v1/recruitment/academic/modalities")
@RequiredArgsConstructor
public class AcademicModalityController {

    private final AcademicModalityService service;

    @GetMapping
    public Flux<AcademicModalityResponse> list() {
        return service.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AcademicModalityResponse> create(@RequestBody @Validated AcademicModalityRequest request) {
        return service.create(request);
    }

    @PatchMapping("/{id}/toggle")
    public Mono<AcademicModalityResponse> toggle(@PathVariable UUID id) {
        return service.toggle(id);
    }
}
