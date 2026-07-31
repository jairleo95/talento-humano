package com.app.recruitment.web;

import com.app.recruitment.service.WorkerService;
import com.app.recruitment.web.dto.WorkerRequest;
import com.app.recruitment.web.dto.WorkerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/api/v1/recruitment/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService service;

    @GetMapping
    public Flux<WorkerResponse> list(@RequestParam(name = "search", required = false) String search) {
        return service.list(search);
    }

    @GetMapping("/{id}")
    public Mono<WorkerResponse> findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<WorkerResponse> create(@RequestBody @Validated WorkerRequest request) {
        return service.create(request);
    }

    @PatchMapping("/{id}")
    public Mono<WorkerResponse> update(@PathVariable UUID id, @RequestBody @Validated WorkerRequest request) {
        return service.update(id, request);
    }
}
