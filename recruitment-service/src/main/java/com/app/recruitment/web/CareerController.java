package com.app.recruitment.web;

import com.app.recruitment.service.CareerService;
import com.app.recruitment.web.dto.CareerRequest;
import com.app.recruitment.web.dto.CareerResponse;
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
@RequestMapping("/api/v1/recruitment/careers")
@RequiredArgsConstructor
public class CareerController {

    private final CareerService service;

    @GetMapping
    public Flux<CareerResponse> list() {
        return service.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CareerResponse> create(@RequestBody @Validated CareerRequest request) {
        return service.create(request);
    }
}
