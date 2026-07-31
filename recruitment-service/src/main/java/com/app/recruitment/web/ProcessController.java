package com.app.recruitment.web;

import com.app.recruitment.service.ProcessService;
import com.app.recruitment.web.dto.ProcessRequest;
import com.app.recruitment.web.dto.ProcessResponse;
import com.app.recruitment.web.dto.ProcessStepRequest;
import com.app.recruitment.web.dto.ProcessStepResponse;
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
@RequestMapping("/api/v1/recruitment/processes")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService service;

    @GetMapping
    public Flux<ProcessResponse> list(@RequestParam(name = "status", required = false) String status) {
        return service.list(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProcessResponse> create(@RequestBody @Validated ProcessRequest request) {
        return service.create(request);
    }

    @PostMapping("/{id}/steps")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProcessStepResponse> addStep(@PathVariable UUID id,
                                             @RequestBody @Validated ProcessStepRequest request) {
        return service.addStep(id, request);
    }

    @GetMapping("/{id}/steps")
    public Flux<ProcessStepResponse> steps(@PathVariable UUID id) {
        return service.steps(id);
    }

    @PatchMapping("/{id}/status")
    public Mono<ProcessResponse> updateStatus(@PathVariable UUID id, @RequestParam("status") String status) {
        return service.updateStatus(id, status);
    }
}
