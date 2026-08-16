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

import com.app.contract.service.TemplateRenderService;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService service;
    private final TemplateRenderService renderService;

    @GetMapping
    public Flux<ContractResponse> list(@RequestParam(name = "requisitionId", required = false) UUID requisitionId) {
        return service.list(requisitionId);
    }

    @GetMapping("/{id}")
    public Mono<ContractResponse> getById(@PathVariable UUID id) {
        return service.list(null)
                .filter(c -> c.id().equals(id))
                .next();
    }

    @GetMapping(value = "/{id}/render", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> render(@PathVariable UUID id) {
        return renderService.renderContract(id);
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

    @GetMapping("/worker/{workerId}/history")
    public Flux<ContractResponse> getWorkerHistory(@PathVariable String workerId) {
        return service.getWorkerHistory(workerId);
    }

    @PatchMapping("/{id}/signed-document")
    public Mono<ContractResponse> uploadSignedDocument(@PathVariable UUID id,
                                                        @RequestParam("fileUrl") String fileUrl,
                                                        @RequestParam(name = "signedBy", defaultValue = "admin") String signedBy) {
        return service.uploadSignedDocument(id, fileUrl, signedBy);
    }

    @PostMapping(value = "/batch-render", produces = MediaType.TEXT_HTML_VALUE)
    public Mono<String> batchRender(@RequestBody java.util.List<UUID> contractIds) {
        return renderService.renderBatchContracts(contractIds);
    }
}
