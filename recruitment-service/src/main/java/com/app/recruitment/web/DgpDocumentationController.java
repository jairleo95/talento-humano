package com.app.recruitment.web;

import com.app.recruitment.service.DgpDocumentationService;
import com.app.recruitment.web.dto.DgpCommentRequest;
import com.app.recruitment.web.dto.DgpCommentResponse;
import com.app.recruitment.web.dto.DgpDocumentRequest;
import com.app.recruitment.web.dto.DgpDocumentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recruitment/requisitions/{requisitionId}")
@RequiredArgsConstructor
public class DgpDocumentationController {

    private final DgpDocumentationService service;

    @GetMapping("/comments")
    public Flux<DgpCommentResponse> listComments(@PathVariable UUID requisitionId) {
        return service.listComments(requisitionId);
    }

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DgpCommentResponse> addComment(@PathVariable UUID requisitionId,
                                                @RequestBody @Validated DgpCommentRequest request) {
        return service.addComment(request);
    }

    @GetMapping("/documents")
    public Flux<DgpDocumentResponse> listDocuments(@PathVariable UUID requisitionId) {
        return service.listDocuments(requisitionId);
    }

    @PostMapping("/documents")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DgpDocumentResponse> addDocument(@PathVariable UUID requisitionId,
                                                  @RequestBody @Validated DgpDocumentRequest request) {
        return service.addDocument(request);
    }
}
