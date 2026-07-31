package com.app.recruitment.web;

import com.app.recruitment.service.CostCenterService;
import com.app.recruitment.web.dto.CostCenterRequest;
import com.app.recruitment.web.dto.CostCenterResponse;
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
@RequestMapping("/api/v1/recruitment/cost-centers")
@RequiredArgsConstructor
public class CostCenterController {

    private final CostCenterService service;

    @GetMapping
    public Flux<CostCenterResponse> list() {
        return service.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CostCenterResponse> create(@RequestBody @Validated CostCenterRequest request) {
        return service.create(request);
    }
}
