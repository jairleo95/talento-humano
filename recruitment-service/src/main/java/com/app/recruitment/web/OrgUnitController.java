package com.app.recruitment.web;

import com.app.recruitment.service.OrgUnitService;
import com.app.recruitment.web.dto.OrgUnitRequest;
import com.app.recruitment.web.dto.OrgUnitResponse;
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
@RequestMapping("/api/v1/recruitment/organizational-units")
@RequiredArgsConstructor
public class OrgUnitController {

    private final OrgUnitService service;

    @GetMapping
    public Flux<OrgUnitResponse> list(@RequestParam(name = "type", required = false) String type,
                                       @RequestParam(name = "parentId", required = false) UUID parentId) {
        if (parentId != null) {
            return service.listByParent(parentId);
        }
        if (type != null) {
            return service.listByType(type);
        }
        return service.listByType(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OrgUnitResponse> create(@RequestBody @Validated OrgUnitRequest request) {
        return service.create(request);
    }

    @PatchMapping("/{id}")
    public Mono<OrgUnitResponse> update(@PathVariable UUID id, @RequestBody OrgUnitRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/toggle")
    public Mono<Void> toggleActive(@PathVariable UUID id) {
        return service.toggleActive(id);
    }
}
