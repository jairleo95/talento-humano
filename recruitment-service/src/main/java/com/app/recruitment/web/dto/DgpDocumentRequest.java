package com.app.recruitment.web.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
public record DgpDocumentRequest(@NotNull UUID requisitionId, @NotBlank String filename, String contentType, String description, String uri, Long sizeBytes) {}

