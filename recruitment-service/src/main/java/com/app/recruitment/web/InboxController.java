package com.app.recruitment.web;

import com.app.recruitment.service.InboxService;
import com.app.recruitment.web.dto.InboxItemRequest;
import com.app.recruitment.web.dto.InboxItemResponse;
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

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recruitment/inbox")
@RequiredArgsConstructor
public class InboxController {

    private final InboxService service;

    @GetMapping
    public Flux<InboxItemResponse> list(@RequestParam(name = "assignee", required = false) String assignee) {
        return service.list(assignee);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<InboxItemResponse> assign(@RequestBody @Validated InboxItemRequest request) {
        return service.assign(request);
    }

    @PatchMapping("/{id}/status")
    public Mono<InboxItemResponse> updateStatus(@PathVariable UUID id, @RequestParam("status") String status) {
        return service.updateStatus(id, status);
    }

    @PatchMapping("/{id}/approve")
    public Mono<InboxItemResponse> approve(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        String comment = body.getOrDefault("comment", "");
        return service.approve(id, comment);
    }

    @PatchMapping("/{id}/reject")
    public Mono<InboxItemResponse> reject(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        String comment = body.getOrDefault("comment", "");
        return service.reject(id, comment);
    }
}
