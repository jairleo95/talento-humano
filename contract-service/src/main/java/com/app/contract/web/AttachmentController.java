package com.app.contract.web;

import com.app.contract.service.AttachmentService;
import com.app.contract.web.dto.AttachmentRequest;
import com.app.contract.web.dto.AttachmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/v1/contracts/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService service;

    @GetMapping
    public Flux<AttachmentResponse> list(@RequestParam(name = "contractId", required = false) UUID contractId) {
        return service.list(contractId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AttachmentResponse> create(@RequestBody @Validated AttachmentRequest request) {
        return service.create(request);
    }
}
