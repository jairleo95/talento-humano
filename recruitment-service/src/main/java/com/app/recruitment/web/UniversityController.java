package com.app.recruitment.web;

import com.app.recruitment.service.UniversityService;
import com.app.recruitment.web.dto.UniversityRequest;
import com.app.recruitment.web.dto.UniversityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/recruitment/universities")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService service;

    @GetMapping
    public Flux<UniversityResponse> list() {
        return service.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UniversityResponse> create(@RequestBody @Validated UniversityRequest request) {
        return service.create(request);
    }
}
