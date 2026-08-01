package com.app.contract.web;

import com.app.contract.service.ContractService;
import com.app.contract.web.dto.ContractRequest;
import com.app.contract.web.dto.ContractResponse;
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
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService service;

    @GetMapping
    public Flux<ContractResponse> list(@RequestParam(name = "requisitionId", required = false) UUID requisitionId) {
        return service.list(requisitionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ContractResponse> create(@RequestBody @Validated ContractRequest request) {
        return service.create(request);
    }

    @PatchMapping("/{id}/sign")
    public Mono<ContractResponse> sign(@PathVariable UUID id) {
        return service.sign(id);
    }

    @PatchMapping("/{id}")
    public Mono<ContractResponse> update(@PathVariable UUID id, @RequestBody ContractRequest request) {
        return service.update(id, request);
    }
}
