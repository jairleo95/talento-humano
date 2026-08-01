package com.app.recruitment.web.dto;
import java.time.Instant;
import java.util.UUID;
public record DgpDocumentResponse(UUID id, UUID requisitionId, String filename, String contentType, String description, String uri, Long sizeBytes, Instant createdAt) {}