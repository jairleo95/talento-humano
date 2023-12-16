package com.app.dgp.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin(origins = "http://localhost:8080/", maxAge = 3600)
@RestController
@RequestMapping("/process")
@AllArgsConstructor
public class ProcessStatusController {

    private final ProcessService processService;

    @GetMapping("dgps")
    public Flux<DGP> getDGPs() {
        return processService.getAllDgp();
    }

    @PostMapping("{id}/status")
    public Mono<ProcessStatusResponse> getStatus(@PathVariable("id") String id, @RequestBody ProcessStatusRequest request){
        return processService.getProcessDetail(id, request.getIddrp(), request.getIddep());
    }
}
