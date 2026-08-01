package com.app.recruitment.service;

import com.app.recruitment.domain.DgpComment;
import com.app.recruitment.domain.DgpDocument;
import com.app.recruitment.persistence.DgpCommentRepository;
import com.app.recruitment.persistence.DgpDocumentRepository;
import com.app.recruitment.web.DgpCommentMapper;
import com.app.recruitment.web.DgpDocumentMapper;
import com.app.recruitment.web.dto.DgpCommentRequest;
import com.app.recruitment.web.dto.DgpCommentResponse;
import com.app.recruitment.web.dto.DgpDocumentRequest;
import com.app.recruitment.web.dto.DgpDocumentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DgpDocumentationService {

    private final DgpCommentRepository commentRepo;
    private final DgpDocumentRepository docRepo;
    private final DgpCommentMapper commentMapper;
    private final DgpDocumentMapper docMapper;

    public Flux<DgpCommentResponse> listComments(UUID requisitionId) {
        return commentRepo.findByRequisitionIdOrderByCreatedAtDesc(requisitionId)
                .map(commentMapper::toResponse);
    }

    public Mono<DgpCommentResponse> addComment(DgpCommentRequest request) {
        DgpComment entity = commentMapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return commentRepo.save(entity).map(commentMapper::toResponse);
    }

    public Flux<DgpDocumentResponse> listDocuments(UUID requisitionId) {
        return docRepo.findByRequisitionIdOrderByCreatedAtDesc(requisitionId)
                .map(docMapper::toResponse);
    }

    public Mono<DgpDocumentResponse> addDocument(DgpDocumentRequest request) {
        DgpDocument entity = docMapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return docRepo.save(entity).map(docMapper::toResponse);
    }
}
