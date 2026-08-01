package com.app.recruitment.web.dto;
import java.time.Instant;
import java.util.UUID;
public record DgpCommentResponse(UUID id, UUID requisitionId, String username, String content, Instant createdAt) {}