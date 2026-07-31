package com.app.contract.web;

import com.app.contract.service.TemplateService;
import com.app.contract.web.dto.TemplateRequest;
import com.app.contract.web.dto.TemplateResponse;
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

@RestController
@RequestMapping("/api/v1/contracts/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService service;

    @GetMapping
    public Flux<TemplateResponse> list(@RequestParam(name = "name", required = false) String name) {
        return service.list(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TemplateResponse> create(@RequestBody @Validated TemplateRequest request) {
        return service.create(request);
    }
}
